
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
/**
 * Write a description of class ShutTheBox here.
 *
 * @author (Abdul Samad)
 * @version (Spring 2018)
 */
public class ShutTheBox
{
    private double LTotalFlipped;
    private double HTotalFlipped;
    private double LSumUnflipped;
    private double HSumUnflipped;
    private double LCon;
    private double HCon;
    private ArrayList <Integer> nums = new ArrayList<Integer>();
    private ArrayList <Integer> nums1 = new ArrayList<Integer>();
    private boolean LResult;
    private boolean HResult;
    Scanner sc = new Scanner (System.in);  
    public ShutTheBox()
    {
        LTotalFlipped =0;
        HTotalFlipped =0;
        LSumUnflipped =0;
        HSumUnflipped =0;
        LCon =0;
        HCon =0;
        LResult = true;
        HResult = true;
    }

    private int getDice()
    {
        Random rand1 = new Random();
        Random rand2 = new Random();
        int ran1 = rand1.nextInt(6)+1;
        int ran2 = rand2.nextInt(6)+1;
        return (ran1+ran2);
    }

    private long concatenate( ArrayList arr)
    {
        String result = "";
        long num =0;
        if (arr.size() == 0)
        {
            result= result + "";
        }
        else
        {
            for (int i = 0; i <arr.size(); i++)
            {
                result= result +arr.get(i);
            }
            num =(long)(Integer.parseInt(result));
        }
        return num;
    }

    private void LisOkay(ArrayList<Integer> arr, int size, int dice, int head, int first, int next, int next2, int x, int y, int i)
    {
        //2 base cases 
        ArrayList<Integer> list = new ArrayList<Integer> ();
        if ((head + next + next2 == dice && size != 2)|| (head + next == dice) || (next == dice || head == dice))
        {
            if (head == dice)
            {
                arr.remove(Integer.valueOf(head));
            }
            else if (next == dice)
            {
                arr.remove(Integer.valueOf(next));
            }
            else if (head+ next == dice) {
                list.add(head);

                for(int ind= 0; ind< list.size();ind++)
                {
                    arr.remove(Integer.valueOf(list.get(ind)));
                }
                list.clear();
                arr.remove(Integer.valueOf(next));
            }
            else
            {
                list.add(head);

                for(int ind= 0; ind< list.size();ind++)
                {
                    arr.remove(Integer.valueOf(list.get(ind)));
                }
                list.clear();
                arr.remove(Integer.valueOf(next));
                arr.remove(Integer.valueOf(next2));
            }
            LResult = true;
            return;
        }

        if ((head + next + next2 > dice || ((y==size-2)|| y==size-1)) && (size !=2))
        {
            x++;
            if((x==size-1)||( x== size -2)){
                i++;
                x=i;
                first=arr.get(x);
                list.add(head);
                head=first;
            }
            else if ( x > size-2)
            {
                list.add(head);
                head = first + arr.get(x-1);
            }
            y = x;
        }

        if ((i == size-2)||( i== size-1))
        {
            LResult = false;
            return;
        }
        y++;
        next = arr.get(y);
        next2 = arr.get(y+1);
        LisOkay(arr,size ,dice, head, first, next, next2, x, y, i);

    }

    private void HisOkay(ArrayList<Integer> arr, int size, int dice, int head, int first, int next, int x, int y, int i)
    {
        //2 base cases 
        //int count = 0;
        ArrayList<Integer> list = new ArrayList<Integer> ();
        if ((head + next == dice && size != 1)|| next == dice || head == dice)
        {
            if (head == dice)
            {
                arr.remove(Integer.valueOf(head));
            }
            else if (next == dice)
            {
                arr.remove(Integer.valueOf(next));
            }
            else {
                list.add(head);

                for(int ind= 0; ind< list.size();ind++)
                {
                    arr.remove(Integer.valueOf(list.get(ind)));
                }
                list.clear();
                arr.remove(Integer.valueOf(next));
            }
            HResult = true;
            return;
        }

        if ((head + next < dice || y==0 && (size !=1)))
        {
            x--;
            //list.add(head);
            if(x==0){
                i--;
                x=i;
                first=arr.get(x);
                head=first;
            }
            else
            {
                head = first+arr.get((arr.indexOf(next)));
            }
            y = x;
        }

        if (i == 0)
        {
            HResult = false;
            return;
        }
        //count++;
        y--;
        //head=first;
        next = arr.get(y);//arr.size()- (arr.indexOf(next))-1);

        HisOkay(arr,size ,dice, head, first, next, x, y, i);

    }

    public void runExperiment()
    {  
        for (int i =1; i < 10; i++)
        {
            nums.add(i);
        }

        for (int i =1; i < 10; i++)
        {
            nums1.add(i);
        }

        System.out.print("Please type in the number of simulations => ");
        long simulations = sc.nextLong();
        for(int index = 0; index < simulations; index++)
        {

            while(LResult)
            {

                int diceNum = getDice();
                // System.out.println("Dice Num= " + diceNum);

                int next = 0;
                int next2 = 0;
                if(nums.size()==0)
                { 
                    break;
                }
                else if (nums.size()==1)
                {
                    next = nums.get(0);
                    next2 = next;
                }
                else if (nums.size()==2)
                {
                    next=nums.get(0);
                    next2= nums.get(1);
                }
                else
                {
                    next = nums.get(1);
                    next2 = nums.get(2);
                }
                int head = nums.get(0);

                LisOkay(nums,nums.size(), diceNum, head, head, next,next2, 0, 0, 0);

            }

            for (int i= 0; i<nums.size(); i++)
            {
                LSumUnflipped = LSumUnflipped + (nums.get(i));
            }

            LTotalFlipped = LTotalFlipped + ( 9 - nums.size());
            LCon = LCon + concatenate(nums);
            LResult = true;
            nums.clear();
            for (int i =1; i < 10; i++)
            {
                nums.add(i);
            }

            while(HResult)
            {

                int diceNum = getDice();
                //System.out.println("Dice Num= " + diceNum);

                int next = 0;
                int head =0;
                if(nums1.size()==0)
                { 
                    break;
                }
                else if (nums1.size()==1)
                {
                    next = nums1.get(0);
                }
                else 
                {
                    next = nums1.get(nums1.size()-2);
                }
                head = nums1.get(nums1.size()-1);
                HisOkay(nums1,nums1.size(), diceNum, head, head, next, nums1.size()-1,nums1.size()-1,nums1.size()-1);
            }

            for (int i= 0; i<nums1.size(); i++)
            {
                HSumUnflipped = HSumUnflipped + (nums1.get(i));
            }
            HTotalFlipped = HTotalFlipped + ( 9 - nums1.size());
            HCon = HCon + concatenate(nums1);
            HResult = true;
            nums1.clear();
            for (int i =1; i < 10; i++)
            {
                nums1.add(i);
            }
        }
        System.out.println("Average numbers flipped is: " + ((double)LTotalFlipped/simulations) + " for lowest choice." );
        System.out.println("Average Sum of unflipped numbers is: " + ((double) LSumUnflipped/simulations)+ " for lowest choice." );
        System.out.println("Average of concatenation numbers " + ((double)(LCon/simulations)) + " for lowest choice." );
        System.out.println("Average numbers flipped is: " + ((double)HTotalFlipped/simulations) + " for highest choice." );
        System.out.println("Average Sum of unflipped numbers is: " + ((double) HSumUnflipped/simulations)+ " for highest choice." );
        System.out.println("Average of concatenation numbers is: " + ((double)(HCon/simulations)) + " for highest choice." );

        LTotalFlipped =0;
        HTotalFlipped =0;
        LSumUnflipped =0;
        HSumUnflipped =0;
        LCon =0;
        HCon =0;
        LResult = true;
        HResult = true;
    }
}
