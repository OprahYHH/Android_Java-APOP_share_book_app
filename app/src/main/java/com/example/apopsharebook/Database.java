package com.example.apopsharebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Database extends SQLiteOpenHelper  {
    List<Books> booksList = new ArrayList<>();
    List<CurrentLoanList> loansList = new ArrayList<>();
    SQLiteDatabase sqLiteDatabase;
    final static String DATABASE_NAME="APOP.db";
    final static int DATABASE_VERSION=28;

    //----------------------------------------CREATING TABLE STRUCTURES---------------------------------------

    //USER TABLE
    final static String U_TABLE="User_table";
    final static String U_UserId="UserId"; //PK - email
    final static String U_Pw="Password";
    final static String U_FName="FName";
    final static String U_LName="LName";
    final static String U_Address="Address";
    final static String U_Age="Age";
    final static String U_UserType="UserType"; //User or Admin

    //BOOK TABLE
    final static String B_TABLE="Book_table";
    final static String B_BookId="BookId"; //PK autoincrement
    final static String B_ISBN="ISBN";  //integer
    final static String B_Title="Title";
    final static String B_Genre="Genre";
    final static String B_Author="Author";
    final static String B_Publisher="Publisher";
    final static String B_PubYear="PubYear";
    final static String B_Location="Location";
    final static String B_OwnerId="OwnerId"; //one of UserIds FK
    final static String B_Status="Status"; //on Loan or Available
    final static String B_Price="Price";

    //LOAN TABLE
    final static String L_TABLE="Loan_table";
    final static String L_LoanId="LoanId"; //PK auto increment
    final static String L_BookId="BookId"; //FK integer
    final static String L_BorrowerId="BorrowerId"; //one of UserIds FK
    final static String L_StartDate="StartDate";
    final static String L_ReturnDate="ReturnDate";
    final static String L_Price="Price";

    //PREFERENCE TABLE
    final static String P_TABLE="Preference_table";
    final static String P_UserId="UserId";
    final static String P_Preference="Preference";

    //READING HISTORY TABLE
    final static String R_TABLE="RHistory_table";
    final static String R_UserId="UserId";
    final static String R_BookId="BookId";
    final static String R_Time="TimeSpent";

    //MESSAGE TABLE
    final static String M_TABLE="Message_table";
    final static String M_MessageId="MessageId"; //PK integer autoincrement
    final static String M_SenderId="SenderId"; //One of userId FK
    final static String M_ReceiverId="ReceiverId"; //One of userId FK
    final static String M_BookId="BookId"; //FK integer
    final static String M_MsgDate="MsgDate";
    final static String M_MsgContent="MsgContent";
    final static String M_Type="MsgType";

    //----------------------------------------------------------------------------------------------

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON");
        }
    }


    //----------------------------------------CREATING TABLES---------------------------------------

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create User Table
       String createTableQuery=" CREATE TABLE "+U_TABLE+"("+ U_UserId+ " text PRIMARY KEY ,"+ U_Pw+
               " text,"+U_FName+" text,"
                +U_LName+" text,"+U_Address+" text,"+U_Age+" int,"+U_UserType+" text)";
        sqLiteDatabase.execSQL(createTableQuery);

        //create Book Table
        createTableQuery=" CREATE TABLE "+B_TABLE+ "("+ B_BookId+ " integer,"+ B_ISBN+" text,"+
                B_Title+" text,"
                +B_Genre+" text,"+B_Author+" text,"+B_Publisher+" text,"+B_PubYear+" integer,"+
                B_OwnerId+" text,"+B_Status+" text,"+B_Location+" text,"+B_Price+" integer,"+
                "PRIMARY KEY ("+ B_BookId+ "), FOREIGN KEY ("+B_OwnerId+") REFERENCES "+U_TABLE+
                " ("+U_UserId+") ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createTableQuery);

        //create Loan Table
        createTableQuery=" CREATE TABLE "+L_TABLE+ "("+ L_LoanId+ " integer,"+ L_BookId+" integer,"+
                L_BorrowerId+" text,"
                +L_StartDate+" text,"+L_ReturnDate+" text,"+L_Price+" text,"+
                "PRIMARY KEY ("+ L_LoanId+ "), FOREIGN KEY ("+L_BookId+") REFERENCES "+B_TABLE+" ("+
                B_BookId+") ON DELETE CASCADE,  " +
                "FOREIGN KEY ("+L_BorrowerId+") REFERENCES "+U_TABLE+" ("+U_UserId+") ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createTableQuery);

        //create Preference Table
        createTableQuery=" CREATE TABLE "+P_TABLE+ "("+ P_UserId+ " text,"+ P_Preference+" text,"+
                "PRIMARY KEY ("+ P_UserId+ ","+P_Preference+"), FOREIGN KEY ("+P_UserId+") " +
                "REFERENCES "+U_TABLE+" ("+U_UserId+") ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createTableQuery);

        //create Reading History Table
        createTableQuery=" CREATE TABLE "+R_TABLE+ "("+ R_UserId+ " text,"+ R_BookId+" integer primary key AUTOINCREMENT,"+
                B_Title+" text,"+B_Author+" text,"+B_ISBN+" text,"+R_Time+ " float,"+
                " FOREIGN KEY ("+R_UserId+") REFERENCES "+
                U_TABLE+" ("+U_UserId+") ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createTableQuery);

        //create Message Table
        createTableQuery=" CREATE TABLE "+M_TABLE+ "("+ M_MessageId+ " integer,"+ M_SenderId+
                " integer,"+ M_ReceiverId+" text,"+M_BookId+" integer,"+ M_MsgDate+" text,"+M_MsgContent+" text,"+ M_Type+" text,"+
                "PRIMARY KEY ("+M_MessageId+"), FOREIGN KEY ("+M_SenderId+") REFERENCES "+U_TABLE+
                " ("+U_UserId+") ON DELETE CASCADE, " +
                "FOREIGN KEY ("+M_ReceiverId+") REFERENCES "+U_TABLE+" ("+U_UserId+") ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createTableQuery);

    }
    //----------------------------------------------------------------------------------------------

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+U_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+B_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+L_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+P_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+R_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+M_TABLE);
        onCreate(sqLiteDatabase);

    }

    //-----------------------------------------ADD BOOK METHOD--------------------------------------

    public boolean addBook(String isbn,String title,String genre,String Author, String Publisher,
                           String PubYear,String OwnerId
    ,String status, String location){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(B_ISBN,isbn);
        value.put(B_Title,title);
        value.put(B_Genre,genre);
        value.put(B_Author,Author);
        value.put(B_Publisher, Publisher);
        value.put(B_PubYear,PubYear);
        value.put(B_OwnerId,OwnerId);
        value.put(B_Status,status);
        value.put(B_Location,location.toLowerCase());
        value.put(B_Price,2);

        long r = sqLiteDatabase.insert(B_TABLE,null,value);
        if(r>0)
            return true;
        else
            return false;
    }
    public boolean addBookToRh(String isbn,String title,String Author, String userId, double timeSpent){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(B_ISBN,isbn);
        value.put(B_Title,title);
        value.put(B_Author,Author);
        value.put(R_UserId,userId);
        value.put(R_Time,timeSpent);
        long r = sqLiteDatabase.insert(R_TABLE,null,value);
        if(r>0)
            return true;
        else
            return false;
    }

    //----------------------------------------SEARCH NAME BY USER ID---------------------------------
    public Cursor getUserInfo(String userID){
        SQLiteDatabase sqdb=this.getReadableDatabase();

        String query="SELECT * FROM "+U_TABLE+" WHERE UserID='"+userID+"'";
        Cursor c=sqdb.rawQuery(query,null);
        return c;
    }
    public Cursor getPreferences(String userID){
        SQLiteDatabase sqdb=this.getReadableDatabase();

        String query="SELECT * FROM "+P_TABLE+" WHERE UserID='"+userID+"'";
        Cursor c=sqdb.rawQuery(query,null);
        return c;
    }

    //----------------------------------------SEARCH BOOKS BY LOCATION & OWNER ID------------------------

    public Cursor searchBookByLocation(String loc,String id){
        SQLiteDatabase sqdb=this.getWritableDatabase();


        String query="SELECT Title, Author, Genre, Publisher, PubYear, OwnerId, Status, BookId, Price FROM "+B_TABLE+" WHERE Location='"+loc+"'"
                +"and OwnerId != '"+ id+"'";
        Cursor c=sqdb.rawQuery(query,null);
        return c;
    }

    //----------------------------------------SEARCH AVAILABLE BOOKS BY LOCATION------------------------
    public Cursor searchAvailableBooksByLoc(String loc,String id){
        SQLiteDatabase sqdb=this.getWritableDatabase();
        String query="SELECT Title, Author, Genre, Publisher, PubYear, OwnerId, Status, BookId, Price FROM "+B_TABLE+" " +
                "WHERE Location='"+loc+"' AND (status='Share' OR status='Give-away') AND OwnerId != '"+id+"'";
        Cursor c=sqdb.rawQuery(query,null);
        return c;
    }

    //----------------------------------------SEARCH ALL AVAILABLE BOOKS IN ALL LOCATIONS---------------
    public Cursor searchAllAvailableBooks(String id){
        SQLiteDatabase sqdb=this.getWritableDatabase();
        String query="SELECT Title, Author, Genre, Publisher, PubYear, OwnerId, Status, BookId, Price FROM "+B_TABLE+
                " WHERE (status='Share' OR status='Give-away') AND OwnerId != '"+id+"'";
        Cursor c=sqdb.rawQuery(query,null);
        return c;
    }

    //----------------------------------------SEARCH BOOKS BY TITLE ----------------------
    public Cursor searchBooksByTitle(String word,String id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT Title, Author, Genre, Publisher, PubYear, OwnerId, Status, BookId, Price FROM Book_table " +
                "WHERE Title LIKE "+"'%"+word+"%' AND(Status='Share' OR Status='Give-away') AND OwnerId != '"+id+"'";
        Cursor c=sqLiteDatabase.rawQuery(query,null);
        return c;
    }


    //----------------------------------------SEARCH RENTAL PRICE BY BOOKID-----------------------------
    public int findPriceByBookId(int bookId){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT Price FROM Book_table WHERE bookId="+bookId;
        Cursor c=sqLiteDatabase.rawQuery(query,null);
        int price=0;
        try{
            while(c.moveToNext()){
                price=c.getInt(0);
            }
        }
        catch (Exception e){
            e.getStackTrace();
        }
        return price;
    }

    //--------------SEND BORROW GIVE-AWAY REQUEST OR PERSONALIZED MESSAGE --------------------------
    public boolean sendMessage(String receiverId, String senderId, String date, int bookId, String content, String type){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(M_MsgContent,content);
        value.put(M_SenderId,senderId);
        value.put(M_ReceiverId, receiverId);
        value.put(M_BookId,bookId);
        value.put(M_MsgDate,date);
        value.put(M_Type,type);
        long r = sqLiteDatabase.insert(M_TABLE,null,value);
        if(r>0)
            return true;
        else
            return false;
    }


    //--------------SEND BORROW GIVE-AWAY REQUEST OR PERSONALIZED MESSAGE --------------------------
    public boolean adminSendMessage(String receiverId, String senderId, String date, int bookId, String content, String type){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(M_MsgContent,content);
        value.put(M_SenderId,senderId);
        value.put(M_ReceiverId, receiverId);
        value.put(M_BookId,bookId);
        value.put(M_MsgDate,date);
        value.put(M_Type,type);
        long r = sqLiteDatabase.insert(M_TABLE,null,value);
        if(r>0)
            return true;
        else
            return false;
    }


    //---------GET MESSAGE TABLE INFO---------------------------------------------------------------
        public Cursor viewMessages(String userId){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT* FROM "+M_TABLE+" WHERE ReceiverId ="+"'"+userId+"'";
        Cursor c=sqLiteDatabase.rawQuery(query,null);

        return c;
        }
    //---------JOIN BOOK TABLE AND MESSGAE TABLE BASED ON A OR D-------------------------------------
    public Cursor requests(String userId){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query=
                "SELECT Message_table.SenderId, Message_table.ReceiverId,  Message_table.MsgType, Book_table.Title " +
                "FROM Message_table, Book_table " +
                 "WHERE ReceiverId ="+"'"+userId+"' AND (MsgType= 'Accepted' OR MsgType='Declined') AND Message_table.bookId=Book_table.bookId";
        Cursor c=sqLiteDatabase.rawQuery(query,null);

        return c;
    }

    //---------Search BOOKID BY MESSAGE ID----------------------------------------------------------
    public int searchBookIdByMessageId(int msgId){
        SQLiteDatabase sqdb=this.getWritableDatabase();
        int id=0;
        String query="SELECT BookId FROM "+M_TABLE+" WHERE MessageId='"+msgId+"'";
        Cursor c=sqdb.rawQuery(query,null);
        try{
            while(c.moveToNext()){
                id=c.getInt(0);
            }
        }
        catch (Exception e){
        e.getStackTrace();
        }
        return id;
    }

    //----------------------------------------------------------------------------------------------

    public List<Books> viewBooks(String id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT Title,Author,Genre,Status,PubYear,FName,ISBN,Publisher,BookId FROM " + B_TABLE+" JOIN "+
                U_TABLE+" ON Book_table.OwnerId = User_table.UserId WHERE Book_table.OwnerId = '"+ id+"'";
        Cursor c =sqLiteDatabase.rawQuery(query,null);

            while(c.moveToNext()){
                String title = c.getString(0);
                String Author = c.getString(1);
                String Genre = c.getString(2);
                String status = c.getString(3);
                String year = c.getString(4);
                String owner = c.getString(5);
                String isbn = c.getString(6);
                String publisher = c.getString(7);
                String bookId = c.getString(8);
                Books book = new Books(title,Author,Genre,status,owner,year,isbn,publisher,bookId);
                booksList.add(book);
            }

        return booksList;
    }

    public List<Books> viewRHBooks(String id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT Title,Author,ISBN,TimeSpent FROM " + R_TABLE+" JOIN "+

                U_TABLE+" ON RHistory_table.UserId = User_table.UserId WHERE RHistory_table.UserId = '"+ id+"'";
        Cursor c =sqLiteDatabase.rawQuery(query,null);

        while(c.moveToNext()){
            String title = c.getString(0);
            String Author = c.getString(1);
            String isbn = c.getString(2);
            double time = c.getDouble(3);
            Books book = new Books(title,Author,isbn,time);
            booksList.add(book);
        }

        return booksList;
    }

    public List<CurrentLoanList> viewClBooks(String id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int[] images = {R.drawable.book_cover_1,R.drawable.book_cover_2,R.drawable.book_cover_3
                ,R.drawable.cover02,R.drawable.cover01,R.drawable.cover03,
                R.drawable.cover04,R.drawable.cover05,R.drawable.cover06,
                R.drawable.cover07,R.drawable.cover08,R.drawable.cover09};


        String query = "SELECT Title,Author,Publisher,PubYear,isbn,BorrowerId,StartDate,ReturnDate,Loan_Table.Price,LoanId FROM " + B_TABLE+" JOIN "+
                L_TABLE +" ON Book_table.BookID = Loan_table.BookId WHERE Loan_table.BorrowerId = '"+id+"'";

        Cursor c = sqLiteDatabase.rawQuery(query,null);
        while(c.moveToNext()){
            Random r = new Random();
            int low = 0;
            int high = 11;
            int img = r.nextInt(high-low) + low;
            String title = c.getString(0);
            String Author = c.getString(1);
            String publisher = c.getString(2);
            String year = c.getString(3);
            String isbn = c.getString(4);
            String BorrowerId = c.getString(5);
            String StartDate = c.getString(6);
            String EndDate = c.getString(7);
            String price = c.getString(8);
            int loanId = c.getInt(9);
            CurrentLoanList loan = new CurrentLoanList(images[img],title,Author,publisher,year,
                    isbn,BorrowerId,StartDate,EndDate,price,loanId );
            loansList.add(loan);
        }
        return  loansList;

    }


    public boolean updateRec(String title, String author, String genre, String status,
                             String year, String isbn,String publisher,String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(B_Title,title);
        values.put(B_Author,author);
        values.put(B_Genre,genre);
        values.put(B_Status,status);
        values.put(B_PubYear,year);
        values.put(B_ISBN,isbn);
        values.put(B_Publisher,publisher);
        int u = sqLiteDatabase.update(B_TABLE,values,"BookId=?",
                new String[]{id});
        if(u>0)
            return true;
        else
            return false;
    }

    public boolean extendDate(String id,String retDate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(L_ReturnDate,retDate);
        int u = sqLiteDatabase.update(L_TABLE,values,"LoanId=?",
                new String[]{id});
        if(u>0)
            return true;
        else
            return false;
    }

    public boolean delBook(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int d = sqLiteDatabase.delete(B_TABLE,"BookId=?",new String[]{id});
        if(d>0)
            return true;
        else
            return false;
    }
    //----------------------------------------UPDATE USER ACCOUNT DETAILS-----------------------------
    public boolean updateUserAccount(String UserId,String password, String fn, String ln,
                             String address, int age){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U_Pw,password);
        values.put(U_FName, fn);
        values.put(U_LName,ln);
        values.put(U_Address,address);
        values.put(U_Age, age);

        int u = sqLiteDatabase.update(U_TABLE,values,"UserId=?",
                new String[]{UserId});
        if(u>0)
            return true;
        else
            return false;
    }

    //----------------------------------------MANUALLY ADD USER /BOOK METHOD------------------------------

    public void addAdmin(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(U_UserId,"Admin");
        value.put(U_Address,"admin");
        value.put(U_Pw,"APOP_admin");
        value.put(U_FName,"admin");
        value.put(U_LName,"admin");
        value.put(U_UserType,"admin");
        value.put(U_Age, 1);
        sqLiteDatabase.insert(U_TABLE,null,value);
    }
    public void manuallyAddUser(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(U_UserId,"iris.grey@abc.com");
        value.put(U_Address,"77th Street, Richmond");
        value.put(U_Pw,"123");
        value.put(U_FName,"Iris");
        value.put(U_LName,"Grey");
        value.put(U_UserType,"user");
        value.put(U_Age, 21);
        sqLiteDatabase.insert(U_TABLE,null,value);

        value.put(U_UserId,"ted.willets@abc.com");
        value.put(U_Address,"23rd Avenue, Brentwood");
        value.put(U_Pw,"321");
        value.put(U_FName,"Ted");
        value.put(U_LName,"Willets");
        value.put(U_UserType,"user");
        value.put(U_Age, 34);
        sqLiteDatabase.insert(U_TABLE,null,value);

    }

    public void manuallyAddBook(){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(B_ISBN,32165222);
        value.put(B_Title,"Waiting for Godot");
        value.put(B_Genre,"Fiction");
        value.put(B_Author,"Samuel Becket");
        value.put(B_Publisher, "Hachette Book");
        value.put(B_PubYear, 2017);
        value.put(B_OwnerId, "iris.grey@abc.com");
        value.put(B_Status,"Share");
        value.put(B_Location,"burnaby");
        value.put(B_Price,1);
        long r = sqLiteDatabase.insert(B_TABLE,null,value);

        value.put(B_ISBN,32165223);
        value.put(B_Title,"Midnight");
        value.put(B_Genre,"Fiction");
        value.put(B_Author,"Matt Haig");
        value.put(B_Publisher, "Viking");
        value.put(B_PubYear, 2020);
        value.put(B_OwnerId, "ted.willets@abc.com");
        value.put(B_Status,"Give-away");
        value.put(B_Location,"brentwood");
        value.put(B_Price,2);
        r = sqLiteDatabase.insert(B_TABLE,null,value);

        value.put(B_ISBN,32165224);
        value.put(B_Title,"One Two Three");
        value.put(B_Genre,"Fiction");
        value.put(B_Author,"Laurie Frankel");
        value.put(B_Publisher,"Henry Holt and Co");
        value.put(B_PubYear,2021);
        value.put(B_OwnerId, "ted.willets@abc.com");
        value.put(B_Status,"Share");
        value.put(B_Location,"richmond");
        value.put(B_Price,2);
        r=sqLiteDatabase.insert(B_TABLE,null,value);
    }
    public void manuallyAddPref() {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(P_UserId,"puru@abcd.com");
        value.put(P_Preference, "Fiction");

        long r = sqLiteDatabase.insert(P_TABLE, null, value);
    }
    public void manuallyAddUser1Pref() {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(P_UserId,"iris.grey@abc.com");
        value.put(P_Preference, "Fiction");
        long r = sqLiteDatabase.insert(P_TABLE, null, value);
    }
    public void manuallyAddUser2Pref() {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(P_UserId,"ted.willets@abc.com");
        value.put(P_Preference, "Non-Fiction");
        long r = sqLiteDatabase.insert(P_TABLE, null, value);
    }

    public boolean addToLoanTable( int bookId, String borrowerID, String startDate, String returnDate,String price){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(L_BookId,bookId);
        value.put(L_BorrowerId,borrowerID);
        value.put(L_StartDate, startDate);
        value.put(L_ReturnDate,returnDate);
        value.put(L_Price,price);
        long r = sqLiteDatabase.insert(L_TABLE,null,value);
        if(r>0)
            return true;
        else
            return false;
    }

    //---------------Insert Data-----------------  U_UserId+ " text PRIMARY KEY,"+ U_Pw+

    public boolean insertData(String username, String password, String fName, String lName, String address, int age){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(U_UserId, username);
        contentValues.put(U_Pw, password);
        contentValues.put(U_FName, fName);
        contentValues.put(U_LName, lName);
        contentValues.put(U_Address, address);
        contentValues.put(U_Age, age);
        contentValues.put(U_UserType,"user");

        Long result = MyDB.insert(U_TABLE, null, contentValues);

        if (result>0)
            return true;
        else
            return false;
    }
    //-------------method for the admin to delete the user form the db
    public void deleteUser(String user){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("DELETE FROM " + U_TABLE + " WHERE " + U_UserId + " = '" + user + "'");

    }

    //---------------check if user exists-------------------

    public boolean checkUsername(String username){
        SQLiteDatabase MyDb = this.getWritableDatabase();
        Cursor cursor = MyDb.rawQuery("Select * from " + U_TABLE + " where " +  U_UserId + " = ? " , new String[] {username});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    //-------------method for the admin to get user details
    public Cursor getUserDetails(String user){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT* FROM "+U_TABLE+" WHERE UserId ="+"'"+user+"'";
        Cursor c=sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    //-------------method for the admin to make updates to the user in the database
    public boolean updateUser(String user, String pwd, String fName, String lName, String add, int age){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U_UserId, user);
        values.put(U_Pw, pwd);
        values.put(U_FName, fName);
        values.put(U_LName, lName);
        values.put(U_Address, add);
        values.put(U_Age, age);

        int u = sqLiteDatabase.update(U_TABLE, values, "UserId = ?", new String[]{user});
        if(u>0)
            return true;
        else
            return false;
    }


    //-------------method for the admin to message a user
    public boolean adminMessage(String receiverId, String senderId, String msg){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(M_MsgContent,msg);
        value.put(M_SenderId,senderId);
        value.put(M_ReceiverId, receiverId);


        long r = sqLiteDatabase.insert(M_TABLE,null,value);
        if(r>0)
            return true;
        else
            return false;
    }
    //--------------login-----------------
    public boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor =  MyDB.rawQuery("select * from " + U_TABLE + " where " + U_UserId + " = ? and " + U_Pw + " = ?", new String[] {username, password });
        if (cursor.getCount() > 0  )
            return true;
        else
            return false ;

    }
    public String getType(String id){
        String type="";
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = "SELECT UserType FROM User_Table WHERE UserId = '"+ id+"'";
        Cursor c = MyDB.rawQuery(query,null);

        while(c.moveToNext()) {
            type = c.getString(0);
        }
        return  type;
    }

    public boolean addPrefs(List<String> pref,String id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Long result = null;
        for(int i = 0;i<pref.size();i++){
            contentValues.put(P_UserId,id);
            contentValues.put(P_Preference,pref.get(i));
            result = MyDB.insert(P_TABLE, null, contentValues);
        }

        if (result==1)
            return false;
        else
            return true;
    }
}