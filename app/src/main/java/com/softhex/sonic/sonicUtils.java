package com.softhex.sonic;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by Administrador on 07/01/2018.
 */

public class sonicUtils {

    private Context myCtx;
    private sonicDatabaseLogCRUD DBCL;
    private sonicSystem mySystem;
    private sonicConstants myCons;
    private Date date = new Date();
    ProgressDialog myProgress;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    Locale brasil = new Locale("pt", "BR");

    sonicUtils(Context context){
        this.myCtx = context;
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.myCons = new sonicConstants();
    }

    Feedback Feedback = new Feedback();
    Arquivo Arquivo = new Arquivo();
    Data Data = new Data();
    Number Number = new Number();

    public static class Randomizer {
        public static int generate(int min,int max) {
            return min + (int)(Math.random() * ((max - min) + 1));
        }
    }
    public static int getFileSize(FTPClient ftpClient, String fileName)
    {
        int fileSize = 0;

        try{
            FTPFile files = ftpClient.mlistFile(fileName);
            return (int)files.getSize();
        }catch (IOException e){
            e.printStackTrace();
        }
        return fileSize;
    }

    public static int countFileLines(File file) throws IOException {
        int lines = 0;

        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[8 * 1024]; // BUFFER_SIZE = 8 * 1024
        int read;

        while ((read = fis.read(buffer)) != -1) {
            for (int i = 0; i < read; i++) {
                if (buffer[i] == '\n') lines++;
            }
        }

        fis.close();

        return lines;
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String bytes2String(long sizeInBytes) {

        double SPACE_KB = 1024;
        double SPACE_MB = 1024 * SPACE_KB;
        double SPACE_GB = 1024 * SPACE_MB;
        double SPACE_TB = 1024 * SPACE_GB;

        NumberFormat nf = new DecimalFormat();
        nf.setMaximumFractionDigits(2);

        try {
            if ( sizeInBytes < SPACE_KB ) {
                return nf.format(sizeInBytes) + " ";
            } else if ( sizeInBytes < SPACE_MB ) {
                return nf.format(sizeInBytes/SPACE_KB) + " KB";
            } else if ( sizeInBytes < SPACE_GB ) {
                return nf.format(sizeInBytes/SPACE_MB) + " MB";
            } else if ( sizeInBytes < SPACE_TB ) {
                return nf.format(sizeInBytes/SPACE_GB) + " GB";
            } else {
                return nf.format(sizeInBytes/SPACE_TB) + " TB";
            }
        } catch (Exception e) {
            return sizeInBytes+" ";
        }

    }

    class Feedback{

            public  boolean statusNetwork() {
                boolean conectado;
                ConnectivityManager conectivtyManager = (ConnectivityManager) myCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (conectivtyManager.getActiveNetworkInfo() != null
                        && conectivtyManager.getActiveNetworkInfo().isAvailable()
                        && conectivtyManager.getActiveNetworkInfo().isConnected()) {
                    conectado = true;
                } else {
                    conectado = false;
                }
                return conectado;
            }

    }

    class Arquivo {

        public int countLines(File file) throws IOException {
            int lines = 0;

            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[8 * 1024]; // BUFFER_SIZE = 8 * 1024
            int read;

            while ((read = fis.read(buffer)) != -1) {
                for (int i = 0; i < read; i++) {
                    if (buffer[i] == '\n') lines++;
                }
            }

            fis.close();

            return lines;
        }

        public void createFile(String nome){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String path = sonicConstants.LOCAL_TMP;
            File file = new File(Environment.getExternalStorageDirectory().toString()+path, nome+".TXT");

            try{

                BufferedWriter output;
                output = new BufferedWriter(new FileWriter(file, true));
                output.append("["+nome+"]");
                output.newLine();
                //output.append();
                output.close();


            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

        }

        public void moveFile(String inputFile) {


            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            InputStream in = null;
            OutputStream out = null;
            inputFile = inputFile+".TXT";

            
            try {

                String path = Environment.getExternalStorageDirectory().toString();

                File sourceLocation = new File(path+myCons.LOCAL_TMP+inputFile);
                File targetLocation = new File (path+myCons.LOCAL_DATA+inputFile);

                sourceLocation.renameTo(targetLocation);

            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

        }

        public void deleteFile(String inputFile) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];

            try {
                new File(inputFile).delete();
            }

            catch (Exception e) {
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
        }

        private void copyFile(String inputPath, String inputFile, String outputPath) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            InputStream in = null;
            OutputStream out = null;
            try {

                java.io.File dir = new java.io.File(outputPath);

                    if (!dir.exists())
                    {
                        dir.mkdirs();
                    }

                    in = new FileInputStream(inputPath + inputFile);
                    out = new FileOutputStream(outputPath + inputFile);

                    byte[] buffer = new byte[1024];
                    int read;
                        while ((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                        }

                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;

                    }  catch (FileNotFoundException e) {
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
                }
                catch (Exception e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();

            }
        }

        class myAsyncTaskUnzip extends AsyncTask<String, String, Boolean>{
            @Override
            protected Boolean doInBackground(String... strings) {

                String toFolder="";

                switch (sonicConstants.DOWNLOAD_TYPE){
                    case "CATALOGO":
                        toFolder = sonicConstants.LOCAL_IMG_CATALOGO;
                        break;
                    case "CLIENTES":
                        toFolder = sonicConstants.LOCAL_IMG_CLIENTES;
                        break;

                }

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                String path = (Environment.getExternalStorageDirectory().toString()+ sonicConstants.LOCAL_TMP);
                String destination = (Environment.getExternalStorageDirectory().toString()+toFolder);
                String zipname = path+strings[0];

                InputStream is;
                ZipInputStream zis;

                try {

                    is = new FileInputStream(zipname);
                    zis = new ZipInputStream(new BufferedInputStream(is));
                    ZipEntry ze;
                    byte[] buffer = new byte[1024];
                    FileOutputStream fout;
                    ZipFile zipFile = new ZipFile(zipname);

                    int count = 0;

                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        entries.nextElement();
                        count++;
                    }

                    myProgress.setMax(count);

                    int count2=0;

                    while ((ze = zis.getNextEntry()) != null) {

                        count2++;
                        publishProgress("Extraindo arquivos...",String.valueOf(count2));
                        /*if (ze.isDirectory()) {
                            java.io.File fmd = new java.io.File(filename);
                            fmd.mkdirs();
                            continue;
                        }*/
                        //ADD THIS//
                        /*if (filename.contains("/")) {
                            String[] folders = filename.split("/");
                            for (String item : folders) {
                                java.io.File fmd = new java.io.File(path + item);
                                if (!item.contains(".") && !fmd.exists()) {
                                    fmd.mkdirs();
                                    Log.d("created folder", item);
                                }
                            }
                        }*/

                        fout = new FileOutputStream(destination + ze.getName());

                        while ((count = zis.read(buffer)) != -1) {
                            fout.write(buffer, 0, count);
                        }

                        fout.close();
                        zis.closeEntry();
                    }

                    zis.close();

                }catch (IOException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                    return false;
                }

                return true;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                myProgress.setMessage(values[0]);
                myProgress.setProgress(Integer.valueOf(values[1]));
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                myProgress.dismiss();
                if(aBoolean){
                    new sonicThrowMessage(myCtx).showMessage("Pronto,","Imagens salvas com sucesso!",sonicThrowMessage.MSG_SUCCESS);
                }
            }
        }

        public void unzipFile(String arquivo) {

            myProgress = new ProgressDialog(myCtx);
            myProgress.setCancelable(false);
            myProgress.setMessage("Extraindo...");
            myProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            myProgress.setProgress(0);
            myProgress.show();

            new myAsyncTaskUnzip().execute(arquivo);

        }

        private String getUriRealPath(Context ctx, Uri uri)
        {
            String ret = "";

            if( isAboveKitKat() )
            {
                // Android OS above sdk version 19.
                ret = getUriRealPathAboveKitkat(ctx, uri);
            }else
            {
                // Android OS below sdk version 19
                ret = getImageRealPath(ctx.getContentResolver(), uri, null);
            }

            return ret;
        }

        private String getUriRealPathAboveKitkat(Context ctx, Uri uri)
        {
            String ret = "";

            if(ctx != null && uri != null) {

                if(isContentUri(uri))
                {
                    if(isGooglePhotoDoc(uri.getAuthority()))
                    {
                        ret = uri.getLastPathSegment();
                    }else if(isNewGooglePhotosUri(uri)) {
                        String pathUri = uri.getPath();
                        String newUri = pathUri.substring(pathUri.indexOf("content")+1, pathUri.lastIndexOf("/ORIGINAL"));
                        ret = getDataColumn(ctx, Uri.parse(newUri), null, null);
                        //ret = newUri;
                    }else{
                        ret = getImageRealPath(ctx.getContentResolver(), uri, null);
                    }

                }else if(isFileUri(uri)) {
                    ret = uri.getPath();
                }else if(isDocumentUri(ctx, uri)){

                    // Get uri related document id.
                    String documentId = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        documentId = DocumentsContract.getDocumentId(uri);
                    }

                    // Get uri authority.
                    String uriAuthority = uri.getAuthority();

                    if(isMediaDoc(uriAuthority))
                    {
                        String idArr[] = documentId.split(":");
                        if(idArr.length == 2)
                        {
                            // First item is document type.
                            String docType = idArr[0];

                            // Second item is document real id.
                            String realDocId = idArr[1];

                            // Get content uri by document type.
                            Uri mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            if("image".equals(docType))
                            {
                                mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            }else if("video".equals(docType))
                            {
                                mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            }else if("audio".equals(docType))
                            {
                                mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            }

                            // Get where clause with real document id.
                            String whereClause = MediaStore.Images.Media._ID + " = " + realDocId;

                            ret = getImageRealPath(ctx.getContentResolver(), mediaContentUri, whereClause);
                        }

                    }else if(isDownloadDoc(uriAuthority))
                    {
                        // Build download uri.
                        Uri downloadUri = Uri.parse("content://downloads/public_downloads");

                        // Append download document id at uri end.
                        Uri downloadUriAppendId = ContentUris.withAppendedId(downloadUri, Long.valueOf(documentId));

                        ret = getImageRealPath(ctx.getContentResolver(), downloadUriAppendId, null);

                    }else if(isExternalStoreDoc(uriAuthority))
                    {
                        String idArr[] = documentId.split(":");
                        if(idArr.length == 2)
                        {
                            String type = idArr[0];
                            String realDocId = idArr[1];

                            if("primary".equalsIgnoreCase(type))
                            {
                                ret = Environment.getExternalStorageDirectory() + "/" + realDocId;
                            }
                        }
                    }
                }
            }
            Log.d("RET",ret);
            return ret;
        }

        public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
            Cursor cursor = null;
            String ret="";
            String column = "_data";
            String[] projection = {
                    column
            };

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndexOrThrow(column);
                    ret = cursor.getString(index);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return ret;
        }

        /* Check whether current android os version is bigger than kitkat or not. */
        private boolean isAboveKitKat()
        {
            boolean ret = false;
            ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
            return ret;
        }

        /* Check whether this uri represent a document or not. */
        private boolean isDocumentUri(Context ctx, Uri uri)
        {
            boolean ret = false;
            if(ctx != null && uri != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ret = DocumentsContract.isDocumentUri(ctx, uri);
                }
            }
            return ret;
        }

        /* Check whether this uri is a content uri or not.
         *  content uri like content://media/external/images/media/1302716
         *  */
        private boolean isContentUri(Uri uri)
        {
            boolean ret = false;
            if(uri != null) {
                String uriSchema = uri.getScheme();
                if("content".equalsIgnoreCase(uriSchema))
                {
                    ret = true;
                }
            }
            return ret;
        }

        /* Check whether this uri is a file uri or not.
         *  file uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
         * */
        private boolean isFileUri(Uri uri)
        {
            boolean ret = false;
            if(uri != null) {
                String uriSchema = uri.getScheme();
                if("file".equalsIgnoreCase(uriSchema))
                {
                    ret = true;
                }
            }
            return ret;
        }


        /* Check whether this document is provided by ExternalStorageProvider. */
        private boolean isExternalStoreDoc(String uriAuthority)
        {
            boolean ret = false;

            if("com.android.externalstorage.documents".equals(uriAuthority))
            {
                ret = true;
            }

            return ret;
        }

        /* Check whether this document is provided by DownloadsProvider. */
        private boolean isDownloadDoc(String uriAuthority)
        {
            boolean ret = false;

            if("com.android.providers.downloads.documents".equals(uriAuthority))
            {
                ret = true;
            }

            return ret;
        }

        /* Check whether this document is provided by MediaProvider. */
        private boolean isMediaDoc(String uriAuthority)
        {
            boolean ret = false;

            if("com.android.providers.media.documents".equals(uriAuthority))
            {
                ret = true;
            }

            return ret;
        }

        /* Check whether this document is provided by google photos. */
        private boolean isGooglePhotoDoc(String uriAuthority)
        {
            boolean ret = false;

            if("com.google.android.apps.photos.content".equals(uriAuthority))
            {
                ret = true;
            }

            return ret;
        }

        public boolean isNewGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.contentprovider".equals(uri.getAuthority());
        }

        /* Return uri represented document file real local path.*/
        private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause)
        {
            String ret = "";

            // Query the uri with condition.
            Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);

            if(cursor!=null)
            {
                boolean moveToFirst = cursor.moveToFirst();
                if(moveToFirst)
                {

                    // Get columns name by uri type.
                    String columnName = MediaStore.Images.Media.DATA;

                    if( uri==MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
                    {
                        columnName = MediaStore.Images.Media.DATA;
                    }else if( uri==MediaStore.Audio.Media.EXTERNAL_CONTENT_URI )
                    {
                        columnName = MediaStore.Audio.Media.DATA;
                    }else if( uri==MediaStore.Video.Media.EXTERNAL_CONTENT_URI )
                    {
                        columnName = MediaStore.Video.Media.DATA;
                    }

                    // Get column index.
                    int imageColumnIndex = cursor.getColumnIndex(columnName);

                    // Get column value which is the uri related file local path.
                    ret = cursor.getString(imageColumnIndex);
                }
            }

            return ret;
        }

        public String getImagePathFromInputStreamUri(Uri uri, String destination, long empresaId, int vendedorId) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            InputStream inputStream = null;
            String filePath = null;

            if (uri.getAuthority() != null) {
                try {
                    inputStream = myCtx.getContentResolver().openInputStream(uri); // context needed
                    File photoFile = createTemporalFileFrom(inputStream, destination, empresaId, vendedorId);

                    filePath = photoFile.getPath();

                } catch (FileNotFoundException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                } catch (IOException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                                e.getMessage(),
                                mySystem.System.getActivityName(),
                                mySystem.System.getClassName(el),
                                mySystem.System.getMethodNames(el));
                        e.printStackTrace();
                    }
                }
            }

            return filePath;
        }

        private File createTemporalFileFrom(InputStream inputStream, String destination, long empresaId, int vendedorId) throws IOException {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            File targetFile = null;

            if (inputStream != null) {
                int read;
                byte[] buffer = new byte[8 * 1024];

                targetFile = createTemporalFile(destination, empresaId, vendedorId);
                OutputStream outputStream = new FileOutputStream(targetFile);

                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                outputStream.flush();

                try {
                    outputStream.close();
                } catch (IOException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }
            }

            return targetFile;
        }

        private File createTemporalFile(String destination, long empresaId, int vendedorId) {
            return new File(Environment.getExternalStorageDirectory().getPath()+destination, "/"+empresaId+"_"+vendedorId+".jpg"); // context needed
        }

        public void saveUriFile(Uri sourceuri, String destination, long empresaId, int vendedorId)
        {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String sourceFilename = getUriRealPath(myCtx,sourceuri);

            if(sourceFilename.equals(null) || sourceFilename.equals("") || sourceFilename.indexOf("content://com.google")==0){

                getImagePathFromInputStreamUri(sourceuri, destination, empresaId, vendedorId);

            }else{

                String destinationFilename = Environment.getExternalStorageDirectory().getPath()+destination+"/"+empresaId+"_"+vendedorId+".jpg";

                Log.d("URI", sourceFilename);
                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;

                try {
                    bis = new BufferedInputStream(new FileInputStream(sourceFilename));
                    bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
                    byte[] buf = new byte[1024];
                    bis.read(buf);
                    do {
                        bos.write(buf);
                    } while(bis.read(buf) != -1);
                } catch (IOException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                } finally {
                    try {
                        if (bis != null) bis.close();
                        if (bos != null) bos.close();
                    } catch (IOException e) {
                        DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                                e.getMessage(),
                                mySystem.System.getActivityName(),
                                mySystem.System.getClassName(el),
                                mySystem.System.getMethodNames(el));
                        e.printStackTrace();
                    }
                }

            }

        }

    }

    class Data{

        public int dateDiffDay(String start, String end){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            int difference = 3;
            Date inicio = new Date();
            Date fim = new Date();

            try{

                inicio = dateFormat.parse(start);
                fim = dateFormat.parse(end);
                difference = ((int)((fim.getTime()/(24*60*60*1000)) - (int)(inicio.getTime()/(24*60*60*1000))));

            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return difference;

        }

        public int dateDiffMonth(String start, String end) {


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            int difference = 0;
            Date inicio = new Date();
            Date fim = new Date();

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Calendar cal = GregorianCalendar.getInstance();
            String ano_atual =  ""+cal.get(Calendar.YEAR);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String hoje =  format.format( date );


            try {

                inicio = dateFormat.parse(start);
                fim = dateFormat.parse(end);
                //difference = monthsBetween(dateFormat.parse(start), dateFormat.parse(end));


            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return difference;

        }

        public String dataFotmatadaBarra(String data){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String dia_ = data;
            String mes_ = data;
            String ano_ = data;
            String data_completa = "";

            try{
                String dia = dia_.substring(0,2);
                String mes = mes_.substring(2,4);
                String ano = ano_.substring(4,8);
                data_completa = dia+"/"+mes+"/"+ano;
            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }



            return data_completa;

        }

        public String dataFotmatadaUS(String data){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String dia_ = data;
            String mes_ = data;
            String ano_ = data;
            String data_completa = "";

            try{
                String dia = dia_.substring(0,2);
                String mes = mes_.substring(2,4);
                String ano = ano_.substring(4,8);
                data_completa = ano+"/"+mes+"/"+dia;
            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return data_completa;

        }

        public String dataFotmatadaUSsemBarra(String data){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String dia_ = data;
            String mes_ = data;
            String ano_ = data;
            String data_completa = "";

            try{
                String dia = dia_.substring(0,2);
                String mes = mes_.substring(2,4);
                String ano = ano_.substring(4,8);
                data_completa = ano+mes+dia;
            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return data_completa;

        }

        public String dataFotmatadaBR(String data){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String dia_ = data;
            String mes_ = data;
            String ano_ = data;
            String data_completa = "";

            try{
                String ano = ano_.substring(0,4);
                String mes = mes_.substring(5,7);
                String dia = dia_.substring(8,10);
                data_completa = dia+"/"+mes+"/"+ano;
            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }



            return data_completa;

        }

        public String horaFotmatadaBR(String horas){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String hora_ = horas;
            String minuto_ = horas;
            String hora_completa = "";

            try{
                String hora = hora_.substring(0,2);
                String minuto = minuto_.substring(0,4);

                hora_completa = hora+":"+minuto;
            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }



            return hora_completa;

        }

        public String stringToDate(String data){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String data_formatada = dataFotmatadaBarra(data);
            String data_final = "";

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(data_formatada);
                data_final = convertedDate.toString();

            } catch (Exception e) {
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return data_final;
        }

        public String hoje(){

            Calendar cal_de = Calendar.getInstance();

            cal_de.set(Calendar.DAY_OF_WEEK, 0);

            return  "='"+simpleDateFormat.format(cal_de.getTime())+"'";


        }

        public String ontem(){

            Calendar cal_de = Calendar.getInstance();

            cal_de.set(Calendar.DAY_OF_WEEK, -1);

            return  "='"+simpleDateFormat.format(cal_de.getTime())+"'";


        }

        public String semanaAtual(){

            Calendar cal_de = Calendar.getInstance();

            cal_de.set(Calendar.DAY_OF_WEEK, cal_de.getFirstDayOfWeek());

            return  ">='"+simpleDateFormat.format(cal_de.getTime())+"'";

        }

        public String semanaAnterior(){

            Calendar cal_de = Calendar.getInstance();

            cal_de.add(Calendar.WEEK_OF_MONTH, -1);
            cal_de.set(Calendar.DAY_OF_WEEK, cal_de.getFirstDayOfWeek());
            String de = simpleDateFormat.format(cal_de.getTime());

            Calendar cal_ate = Calendar.getInstance();

            cal_ate.add(Calendar.WEEK_OF_MONTH, -1);
            cal_ate.set(Calendar.DAY_OF_WEEK, +7);
            String ate = simpleDateFormat.format(cal_ate.getTime());

            return "BETWEEN '"+simpleDateFormat.format(cal_de.getTime())+"' AND '"+simpleDateFormat.format(cal_ate.getTime())+"'";

        }


        public String mesAtual(){

            Calendar cal_de = Calendar.getInstance();

            cal_de.add(Calendar.MONTH, 0);
            cal_de.set(Calendar.DAY_OF_MONTH, cal_de.getActualMinimum(Calendar.DAY_OF_MONTH));

            return  ">='"+simpleDateFormat.format(cal_de.getTime())+"'";

        }

        public String mesAnterior(){

            Calendar cal_de = Calendar.getInstance();

            cal_de.add(Calendar.MONTH, -1);
            cal_de.set(Calendar.DAY_OF_MONTH, cal_de.getActualMinimum(Calendar.DAY_OF_MONTH));

            Calendar cal_ate = Calendar.getInstance();

            cal_ate.add(Calendar.MONTH, -1);
            cal_ate.set(Calendar.DAY_OF_MONTH, cal_ate.getActualMaximum(Calendar.DAY_OF_MONTH));

            return  "BETWEEN '"+simpleDateFormat.format(cal_de.getTime())+"' AND '"+simpleDateFormat.format(cal_ate.getTime())+"'";

        }

        public String tresMeses(){

            Calendar cal_de = Calendar.getInstance();

            cal_de.add(Calendar.MONTH, -3);
            cal_de.set(Calendar.DAY_OF_MONTH, cal_de.getActualMinimum(Calendar.DAY_OF_MONTH));

            return ">='"+simpleDateFormat.format(cal_de.getTime())+"'";

        }


    }


    class Number{

            public String stringToDecimal(String number) {

                double value = Double.valueOf(number);
                DecimalFormat decimalFormat = new DecimalFormat("#.##0,00");
                String stringAsNumber = decimalFormat.format(value);

               return stringAsNumber;

            }

        public String stringToMoeda(String number) {



            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String sem_virgula = number.replace(',','.');

            Locale BRAZIL = new Locale("pt","BR");
            DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);
            DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ #,##0.00",REAL);
            Double valor = 0d;

            int tamanho = number.length();

            try{

                String numero_final = number.substring(0, tamanho-2)+"."+number.substring(tamanho-2, tamanho);

                valor = Double.valueOf(numero_final);

            }catch (Exception e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return DINHEIRO_REAL.format(valor);

        }


    }

}
