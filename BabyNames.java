import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * Write a description of BabyNames here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyNames {
    
    public void printNames(CSVParser parser){
        for(CSVRecord rec:parser){
            System.out.println("Name: "+rec.get(0)+" Gender: "+rec.get(1)+" Total number:"+rec.get(2));
        }
    }
    
    public void testPrintNames(){
        FileResource fr=new FileResource();
        printNames(fr.getCSVParser(false));
    }
    
    public void totalBirths(CSVParser parser){
        int mCount=0,fCount=0,totalCount=0;
        for(CSVRecord rec:parser){
            totalCount++;
            if(rec.get(1).equals("M")){
                mCount++;
            }
            else{
                fCount++;
            }
        }
        System.out.println("Total Baby Names: "+totalCount+"\nMale baby names: "+mCount+"\nFemale baby names: "+fCount);
    }
    
    public void testTotalBirths(){
        FileResource fr=new FileResource();
        totalBirths(fr.getCSVParser(false));
    }

    public int getRank(int year,String name,String gender){
        String path="us_babynames/us_babynames_by_year/yob"+year+".csv";
        //System.out.println("Path: "+path);
        FileResource fr=new FileResource();
        CSVParser parser=fr.getCSVParser(false);
        int rank=0;
        for(CSVRecord rec:parser){
            if(rec.get(1).equals(gender)){
                rank++;
                if(rec.get(0).equals(name)){
                    return rank;
                }
            }
        }
        return -1;
    }
    
    public String getName(int year,int rank,String gender){
        String name="NO NAME FOUND";
        int countRank=0;
        FileResource fr=new FileResource();
        CSVParser parser=fr.getCSVParser(false);
        for(CSVRecord rec:parser){
            if(rec.get(1).equals(gender)){
                countRank++;
                if(countRank==rank){
                    return rec.get(0);
                }
            }
        }
        return name;
    }
    
    public void whatIsNameInYear(String name,int year,int newYear, String gender){
        int currentRank=getRank(year,name,gender);
        String newName=getName(newYear,currentRank,gender);
        System.out.println(name+" born in "+year+" would be "+newName+" if she was born in "+newYear);
        
    }
    
    public int yearOfHighestRank(String name,String gender){
        DirectoryResource dr=new DirectoryResource();
        int lowestRank=-1,currentFileRank=0,lowestRankYear=-1;
        for(File f:dr.selectedFiles()){
            currentFileRank=getRank(1990,name,gender);
            if(currentFileRank != -1){
                if(lowestRank == -1 || currentFileRank<lowestRank){
                    lowestRank=currentFileRank;
                    lowestRankYear=Integer.parseInt((f.getName()).substring(3,7));
                }
            }
        }
        return lowestRankYear;
    }
    
    public double getAverageRank(String name,String gender){
        DirectoryResource dr=new DirectoryResource();
        double avgRank=0.0;
        int currentFileRank=0,fileCount=0;
        for(File f:dr.selectedFiles()){
            fileCount++;
            currentFileRank=getRank(1990,name,gender);
            if(currentFileRank != -1){
                    avgRank+=currentFileRank;
            }
        }
        if(avgRank!=-1.0)
        {return avgRank/fileCount;}
        return -1;
    }
    
    public int getTotalBirthsRankedHigher(int year,String name,String gender){
        DirectoryResource dr=new DirectoryResource();
        int totalBirths=0,inputRank=0,currentCount=0;
        FileResource fr=new FileResource();
        CSVParser parser=fr.getCSVParser();
        inputRank=getRank(1990,name,gender);
        
        for(CSVRecord record:parser){
            if(record.get(1).equals(gender)){
                currentCount++;
                if(currentCount<inputRank){
                    totalBirths+=Integer.parseInt(record.get(2));
                }
            }
        }
        return totalBirths;
    }
    
    public void testGetName(){
        System.out.println("The male name at rank 2 in 2012 is "+getName(2012,2,"M"));
    }
    
    public void testGetRank(){
        System.out.println("The rank for Mason in 2012 is: "+getRank(2012,"Mason","M"));
    }
    public void testWhatisNameInYear(){
        whatIsNameInYear("Isabella",2012,2014,"F");
    }
    public void testHighestRankSoFar(){
        System.out.println("The year of highest rank so far is "+yearOfHighestRank("Mason","M"));
    }
    public void testAvgRank(){
        System.out.println("Average rank is "+getAverageRank("Mason","M"));
    }
    public void testTotalBirthsRankedHigher(){
        System.out.println("Total births: "+ getTotalBirthsRankedHigher(1999,"Ethan","M"));
    }
}
