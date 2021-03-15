import java.lang.Math;
import java.util.Random;
public class BracketOptimizer
{
    private static final double PYTH_EXP=13.91;
    private static final int FIRST_RND_PTS=1;
    private static final int SCND_RND_PTS=2;
    private static final int THIRD_RND_PTS=4;
    private static final int FOURTH_RND_PTS=8;
    private static final int FIFTH_RND_PTS=16;
    private static final int SIXTH_RND_PTS=32;
    //presence of seed bonuses--1 means it exists and 0 means it doesn't
    private static final int FIRST_BONUS=0;
    private static final int SCND_BONUS=0;
    private static final int THIRD_BONUS=0;
    private static final int FOURTH_BONUS=0;
    private static final int FIFTH_BONUS=0;
    private static final int SIXTH_BONUS=0;
    public static boolean searchArray(String[] arr,String name) //returns true if name is found
    {
        for(String n:arr) {
        if(n.equals(name))
        return true;}
        return false;
    }
    public static int findIndex(String[] arr, String key) 
    {
        for(int n=0;n<arr.length;n++)
        {
            if(key.equals(arr[n]))
            return n;
        }
        return -1;
    }
    public static boolean getPick(double pctA, double pctB) //returns true if team A is picked(pctA)
    {
        double probA=pctA/(pctA+pctB);
        if(Math.random()>probA)
            return false;
           return true;
    }
    public static double getWinProb(double a,double b) //returns prob that team a wins
    {
        double exp = -1.0 * ((a-b)* 30.464 / 400.0);
        return 1.0 / (1.0 + Math.pow(10.0, exp));
    }
    public static boolean getGameResult(double a, double b) //returns true if team a wins
    {
        double winProbA=getWinProb(a,b);
        if(Math.random()<winProbA)
        return true;
        else
        return false;
    }
    public static String[][] generatePicks(String [] nms,double[] r1pPicks,double[] r2pPicks,double[] r3pPicks,double[] r4pPicks,
    double[] r5pPicks, double[] r6pPicks)
    {
        String[]firstrnd=new String[32];
        int[] firstrndIDs=new int[32];
        String[]scndrnd=new String[16];
        int[] scndrndIDs=new int[16];
        String[]thirdrnd=new String[8];
        int[] thirdrndIDs=new int[8];
        String[]fourthrnd=new String[4];
        int[] fourthrndIDs=new int[4];
        String[]fifthrnd=new String[2];
        int[] fifthrndIDs=new int[2];
        String[]sixthrnd=new String[1];
        int k=0;
        for(int i=0;i<nms.length-1;i+=2) //first round picks 
        {
         if(BracketOptimizer.getPick(r1pPicks[i],r1pPicks[i+1])==true)
        {
            firstrnd[k]=nms[i];
            firstrndIDs[k]=i;
        } 
         else
        {
            firstrnd[k]=nms[i+1];
            firstrndIDs[k]=i+1;
        }
        k++;
        }
        k=0;
        for(int i=0;i<firstrnd.length-1;i+=2) //second round picks
        {
            if(BracketOptimizer.getPick(r2pPicks[firstrndIDs[i]],r2pPicks[firstrndIDs[i+1]])==true)
            {
                scndrnd[k]=nms[firstrndIDs[i]];
                scndrndIDs[k]=firstrndIDs[i];
            }
            else
            {
                scndrnd[k]=nms[firstrndIDs[i+1]];
                scndrndIDs[k]=firstrndIDs[i+1];
            }
            k++;
        }
        k=0;
        for(int i=0;i<scndrnd.length-1;i+=2) //third round picks
        {
            if(BracketOptimizer.getPick(r3pPicks[scndrndIDs[i]],r3pPicks[scndrndIDs[i+1]])==true)
            {
                thirdrnd[k]=nms[scndrndIDs[i]];
                thirdrndIDs[k]=scndrndIDs[i];
            }
            else
            {
                thirdrnd[k]=nms[scndrndIDs[i+1]];
                thirdrndIDs[k]=scndrndIDs[i+1];
            }
            k++;
        }
        k=0;
        for(int i=0;i<thirdrnd.length-1;i+=2) //fourth round picks
        {
            if(BracketOptimizer.getPick(r4pPicks[thirdrndIDs[i]],r4pPicks[thirdrndIDs[i+1]])==true)
            {
                fourthrnd[k]=nms[thirdrndIDs[i]];
                fourthrndIDs[k]=thirdrndIDs[i];
            }
            else
            {
                fourthrnd[k]=nms[thirdrndIDs[i+1]];
                fourthrndIDs[k]=thirdrndIDs[i+1];
            }
            k++;
        }
        k=0;
        for(int i=0;i<fourthrnd.length-1;i+=2) //fifth round picks
        {
            if(BracketOptimizer.getPick(r5pPicks[fourthrndIDs[i]],r5pPicks[fourthrndIDs[i+1]])==true)
            {
                fifthrnd[k]=nms[fourthrndIDs[i]];
                fifthrndIDs[k]=fourthrndIDs[i];
            }
            else
            {
                fifthrnd[k]=nms[fourthrndIDs[i+1]];
                fifthrndIDs[k]=fourthrndIDs[i+1];
            }
            k++;
        }
        k=0;
        for(int i=0;i<fifthrnd.length-1;i+=2) //championship pick
        {
            if(BracketOptimizer.getPick(r6pPicks[fifthrndIDs[i]],r6pPicks[fifthrndIDs[i+1]])==true)
            {
                sixthrnd[k]=nms[fifthrndIDs[i]];
            }
            else
            {
               sixthrnd[k]=nms[fifthrndIDs[i+1]];
            }
            k++;
        }
        String[][] fullPicks={firstrnd,scndrnd,thirdrnd,fourthrnd,fifthrnd,sixthrnd};
        return fullPicks;
    }
    public static String[][] simTourney(String[] names,double[] ppg) //returns correct bracket 
    {
        double [] rnd32=new double[32];
        String [] rnd32names=new String[32];
        int y=0;
        for(int x=0;x<ppg.length-1;x+=2)
        {
            if(BracketOptimizer.getGameResult(ppg[x],ppg[x+1])==true) {
            rnd32[y]=ppg[x];
            rnd32names[y]=names[x]; }
            else {
            rnd32[y]=ppg[x+1];
            rnd32names[y]=names[x+1]; }
            y++;
        }
        double [] sweet16=new double[16];
        String [] sweet16Names=new String[16];
        int i=0;
        for(int x=0;x<rnd32.length-1;x+=2)
        {
            if(BracketOptimizer.getGameResult(ppg[x],ppg[x+1])==true) {
            sweet16[i]=rnd32[x];
            sweet16Names[i]=rnd32names[x]; }
            else {
            sweet16[i]=rnd32[x+1];
            sweet16Names[i]=rnd32names[x+1]; }
            i++;
        }
       
        double [] eliteEight=new double[8];
        String [] eliteEightNames=new String[8];
        int g=0;
        for(int x=0;x<sweet16.length-1;x+=2)
        {
            if(BracketOptimizer.getGameResult(ppg[x],ppg[x+1])==true) {
            eliteEight[g]=sweet16[x];
            eliteEightNames[g]=sweet16Names[x]; }
            else {
            eliteEight[g]=sweet16[x+1];
            eliteEightNames[g]=sweet16Names[x+1]; }
            g++;
        }
        double []  finalFour=new double[4];
        String [] finalFourNames=new String[4];
        int l=0;
        for(int x=0;x<eliteEight.length-1;x+=2)
        {
            if(BracketOptimizer.getGameResult(ppg[x],ppg[x+1])==true) {
            finalFour[l]=eliteEight[x];
            finalFourNames[l]=eliteEightNames[x]; }
            else {
            finalFour[l]=eliteEight[x+1];
            finalFourNames[l]=eliteEightNames[x+1]; }
            l++;
        }
        double [] finl=new double[2];
        String [] finlNames=new String[2];
        int a=0;
        for(int x=0;x<finalFour.length-1;x+=2)
        {
            if(BracketOptimizer.getGameResult(finalFour[x],finalFour[x+1])==true) {
            finl[a]=finalFour[x];
            finlNames[a]=finalFourNames[x];
        }
            else {
            finl[a]=finalFour[x+1];
            finlNames[a]=finalFourNames[x+1]; }
            a++;
        }
        String[] champ=new String[1];
        if(BracketOptimizer.getGameResult(finl[0],finl[1])==true)
        champ[0]=finlNames[0];
        else
        champ[0]=finlNames[1];
        String[][] bracket= {rnd32names,sweet16Names,eliteEightNames,finalFourNames,finlNames,champ};
        return bracket;
    }
    public static int getGameScore(String[][] picks,String[][] correctBracket, String[] names) //returns game score
    {
        int gameScore=0;
        int[] seedOrder={1,16,8,9,5,12,4,13,3,14,6,11,7,10,2,15,1,16,8,9,5,12,4,13,3,14,6,11,7,10,2,15,
            1,16,8,9,5,12,4,13,3,14,6,11,7,10,2,15,1,16,8,9,5,12,4,13,3,14,6,11,7,10,2,15};
        for(int x=0;x<32;x++)
        {
            if(picks[0][x].equals(correctBracket[0][x]))
            {
            gameScore+=FIRST_RND_PTS;
            
            if(FIRST_BONUS==1)
            gameScore+=seedOrder[BracketOptimizer.findIndex(names,picks[0][x])];
            }
        }
        for(int x=0;x<16;x++)
        {
            if(picks[1][x].equals(correctBracket[1][x]))
            {
            gameScore+=SCND_RND_PTS;
            
            if(SCND_BONUS==1)
            gameScore+=seedOrder[BracketOptimizer.findIndex(names,picks[1][x])];
            }
        }
        for(int x=0;x<8;x++)
        {
            if(picks[2][x].equals(correctBracket[2][x]))
            {
            gameScore+=THIRD_RND_PTS;
            
            if(THIRD_BONUS==1)
            gameScore+=seedOrder[BracketOptimizer.findIndex(names,picks[2][x])];
            }
        }
        for(int x=0;x<4;x++)
        {
            if(picks[3][x].equals(correctBracket[3][x]))
            {
            gameScore+=FOURTH_RND_PTS;
            
            if(FOURTH_BONUS==1)
            gameScore+=seedOrder[BracketOptimizer.findIndex(names,picks[3][x])];
            }
        }
        for(int x=0;x<2;x++)
        {
            if(picks[4][x].equals(correctBracket[4][x]))
            {
            gameScore+=FIFTH_RND_PTS;
            
            if(FIFTH_BONUS==1)
            gameScore+=seedOrder[BracketOptimizer.findIndex(names,picks[4][x])];
            }
        }
        for(int x=0;x<1;x++)
        {
            if(picks[5][x].equals(correctBracket[5][x]))
            {
            gameScore+=SIXTH_RND_PTS;
            
            if(SIXTH_BONUS==1)
            gameScore+=seedOrder[BracketOptimizer.findIndex(names, picks[5][x])];
            }
        }
        return gameScore;
    }
    public static int getUserPlace(String[][] userPicks,String[][][] otherPicks,String[][] correctBracket, String[] names) 
    {
        int myGameScore=BracketOptimizer.getGameScore(userPicks,correctBracket,names);
        int counter=1;
        for(int c=0;c<otherPicks.length;c++)
        {
            if(BracketOptimizer.getGameScore(otherPicks[c],correctBracket,names)>myGameScore)
            counter++;
        }
        return counter;
    }
    public static int getBracketWinPct(String[][] userPicks,int size,String[] nms,double[] r1pPicks, double[] r2pPicks,
    double[] r3pPicks, double[] r4pPicks, double[] r5pPicks, double[] r6pPicks, double[] rtngs,int bestpct)
    {
        //edit this function if there is a specific payout structure, rather than just focusing on winning\
        //curently is modified for a payout structure and returns total payout from 50000 sims.  Old code is commented out
        int wins=0;
        //temporarily changed to 10,000 simulations to make it run faster with a large pool (from 50,000)-->changed back
        String[][][] otherPicks=new String[size-1][6][32];
        String[][] correct= new String[6][32];
        for(int i=0;i<1000;i++)
        {
            for(int p=0;p<size-1;p++) //get other picks
        {
            otherPicks[p]=BracketOptimizer.generatePicks(nms,r1pPicks,r2pPicks,r3pPicks,r4pPicks,r5pPicks,r6pPicks);
        }
        correct=BracketOptimizer.simTourney(nms,rtngs);
        if(BracketOptimizer.getUserPlace(userPicks,otherPicks,correct,nms)==1) {
            wins++;
        }

        }
        return wins/10;
    }
    public static String[][] findOptimalBracket(int pSize, String[] nms, double[] r1pPicks, double[] r2pPicks,
    double[] r3pPicks, double[] r4pPicks, double[] r5pPicks, double[] r6pPicks, double[] rtngs)
    {
        String[][] userPicks=new String[6][32];
        String [][] testPicks=new String[6][32];
        double[][] probabilites={r1pPicks, r2pPicks,r3pPicks,r4pPicks,r5pPicks,r6pPicks};
        int index;
        String[][][] otherPicks=new String[pSize][6][32];
        String [][] correct= new String[6][32];
        int testPct;
        int stable = 0; //counts amount of times bracket is "stable" aka no changes were made
    for(int x=0;x<testPicks.length;x++)
    for(int y=0;y<testPicks[x].length;y++)
    {
    testPicks[x][y]="";
    }
    String[][] starter=BracketOptimizer.generatePicks(nms,r1pPicks,r2pPicks,r3pPicks,r4pPicks,r5pPicks,r6pPicks);
   for(int x=0;x<testPicks.length;x++)
    for(int y=0;y<32/(Math.pow(2,x));y++)
    {
        testPicks[x][y]=starter[x][y];
    }
    for(int x=0;x<testPicks.length;x++)
    for(int y=0;y<testPicks[x].length;y++)
    {
        userPicks[x][y]=testPicks[x][y];
    }
    int bestPct=0;
    for(int z=0;z<50;z++)
    {
        int changes = 0; //code for testing
    for(int i=0;i<64;i++) //find champion
    {
        for(int r=0;r<6;r++) //put potential champ in bracket
        {
            index=(int) (i/(Math.pow(2,r+1)));
            testPicks[r][index]=nms[i];
        }
        testPct=BracketOptimizer.getBracketWinPct(testPicks,pSize,nms,r1pPicks,r2pPicks,r3pPicks,r4pPicks,r5pPicks,r6pPicks,rtngs,bestPct);
        if(testPct>bestPct)
        {
            changes++;
            bestPct=testPct;
        for(int x=0;x<testPicks.length;x++)
            for(int y=0;y<testPicks[x].length;y++)
            {
                userPicks[x][y]=testPicks[x][y];
            }
        }
        else
        {
        for(int x=0;x<userPicks.length;x++)  
        for(int y=0; y<userPicks[x].length;y++)
        testPicks[x][y]=userPicks[x][y];
        }       
     }
        int r5start=0;
    if(BracketOptimizer.findIndex(nms,userPicks[5][0])<=31)
    {
        r5start=32;
    }
    for(int i=r5start;i<r5start+32;i++) //find runner up 
    {
        for(int r=0;r<5;r++) //puts team in bracket
        {
            index= (int) (i/(Math.pow(2,r+1)));
            testPicks[r][index]=nms[i];
        }
        testPct=BracketOptimizer.getBracketWinPct(testPicks,pSize,nms,r1pPicks,r2pPicks,r3pPicks,r4pPicks,r5pPicks,r6pPicks,rtngs,bestPct);
        if(testPct>bestPct)
        {
            changes++;
            bestPct=testPct;
        for(int x=0;x<testPicks.length;x++)
            for(int y=0;y<testPicks[x].length;y++)
            {
                userPicks[x][y]=testPicks[x][y];
            }
        }
        else
        {
        for(int x=0;x<userPicks.length;x++)  
        for(int y=0; y<userPicks[x].length;y++)
        testPicks[x][y]=userPicks[x][y];
        }   
    }
    int[] r4starts={0,0};
    for(int k=0;k<r4starts.length;k++)
    {
        if(BracketOptimizer.findIndex(nms,userPicks[4][k])>(15+(k*32)))
        {
            r4starts[k]=k*32;
        }
        else
        {
            r4starts[k]=(k*32)+16; 
        }
    }
    for(int t=0;t<r4starts.length;t++) //find remaining final four teams
    {
        for(int i=r4starts[t];i<r4starts[t]+16;i++)
        {
        for(int r=0;r<4;r++) 
            {
                index=(int) (i/(Math.pow(2,r+1)));
                testPicks[r][index]=nms[i];
            }
        testPct=BracketOptimizer.getBracketWinPct(testPicks,pSize,nms,r1pPicks,r2pPicks,r3pPicks,r4pPicks,r5pPicks,r6pPicks,rtngs,bestPct);
        if(testPct>bestPct)
        {
            changes++;
        bestPct=testPct;
        
        for(int x=0;x<testPicks.length;x++)
        {
            for(int y=0;y<testPicks[x].length;y++)
            {
                userPicks[x][y]=testPicks[x][y];
            }
        }
        }
        else
        {
        for(int x=0;x<userPicks.length;x++)  
        for(int y=0; y<userPicks[x].length;y++)
        testPicks[x][y]=userPicks[x][y];
        }   
        }
    }
    int[] r3starts={0,0,0,0};
    for(int k=0;k<r3starts.length;k++)
    {
        if(BracketOptimizer.findIndex(nms,userPicks[3][k])>(7+(k*16)))
        {
            r3starts[k]=k*16;
        }
        else
        {
            r3starts[k]=(k*16)+8; 
        }
    }
    for(int t=0;t<r3starts.length;t++) //find remaining elite eight teams
    {
        for(int i=r3starts[t];i<r3starts[t]+8;i++)
        {
        for(int r=0;r<3;r++) 
            {
                index=(int) (i/(Math.pow(2,r+1)));
                testPicks[r][index]=nms[i];
            }
        testPct=BracketOptimizer.getBracketWinPct(testPicks,pSize,nms,r1pPicks,r2pPicks,r3pPicks,r4pPicks,r5pPicks,r6pPicks,rtngs,bestPct);
        if(testPct>bestPct)
        {
            changes++;
         bestPct=testPct;
        for(int x=0;x<testPicks.length;x++)
            for(int y=0;y<testPicks[x].length;y++)
            {
                userPicks[x][y]=testPicks[x][y];
            }
        }
        else
        {
        for(int x=0;x<userPicks.length;x++)  
        for(int y=0; y<userPicks[x].length;y++)
        testPicks[x][y]=userPicks[x][y];
        }   
        }
    }
    int[] r2starts=new int[8];
    for(int k=0;k<r2starts.length;k++)
    {
        if(BracketOptimizer.findIndex(nms,userPicks[2][k])>((k*8)+3))
        {
            r2starts[k]=k*8;
        }
        else
        {
            r2starts[k]=(k*8)+4;
        }
    }
    for(int t=0;t<r2starts.length;t++)
    {
        for(int i=r2starts[t];i<r2starts[t]+4;i++)
        {
        for(int r=0;r<2;r++) 
            {
                index=(int) (i/(Math.pow(2,r+1)));
                testPicks[r][index]=nms[i];
            }
        testPct=BracketOptimizer.getBracketWinPct(testPicks,pSize,nms,r1pPicks,r2pPicks,r3pPicks,r4pPicks,r5pPicks,r6pPicks,rtngs,bestPct);
        if(testPct>bestPct)
        {
            changes++;
        bestPct=testPct; 
        for(int x=0;x<testPicks.length;x++)
            for(int y=0;y<testPicks[x].length;y++)
            {
                userPicks[x][y]=testPicks[x][y];
            }
        }
        else
        {
        for(int x=0;x<userPicks.length;x++)  
        for(int y=0; y<userPicks[x].length;y++)
        testPicks[x][y]=userPicks[x][y];
        }   
        }
    }
    int [] r1starts=new int[16];
    for(int k=0;k<r1starts.length;k++)
    {
        if(BracketOptimizer.findIndex(nms,userPicks[1][k])>((k*4)+1))
        {
            r1starts[k]=k*4;
        }
        else
        {
            r1starts[k]=(k*4)+2;
        }
    }
    for(int t=0;t<r1starts.length;t++)
    {
        for(int i=r1starts[t];i<r1starts[t]+2;i++)
        {
        for(int r=0;r<1;r++) 
            {
                index=(int) (i/(Math.pow(2,r+1)));
                testPicks[r][index]=nms[i];
            }
        testPct=BracketOptimizer.getBracketWinPct(testPicks,pSize,nms,r1pPicks,r2pPicks,r3pPicks,r4pPicks,r5pPicks,r6pPicks,rtngs,bestPct);
        if(testPct>bestPct)
        {
            changes++;
        bestPct=testPct;
        for(int x=0;x<testPicks.length;x++)
            for(int y=0;y<testPicks[x].length;y++)
            {
                userPicks[x][y]=testPicks[x][y];
            }
        }
        else
        {
        for(int x=0;x<userPicks.length;x++)  
        for(int y=0; y<userPicks[x].length;y++)
        testPicks[x][y]=userPicks[x][y];
        }   
        }
    }
    if(changes == 0)
        stable++;
    if(stable >= 3) {
        userPicks[5][1] = " "+bestPct;
        return userPicks; }
   }
    userPicks[5][1]=" "+bestPct;
    return userPicks;
} 
}