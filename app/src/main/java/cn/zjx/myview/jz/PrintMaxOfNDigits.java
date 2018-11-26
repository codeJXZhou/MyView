package cn.zjx.myview.jz;

/**
 * @author zjx on 2018/9/12.
 */
public class PrintMaxOfNDigits {
    public boolean Increment(int[] number) {                 //  这个方法是用来实现对数加1操作
        boolean isOverflow = false;
        int nTakeOver = 0;
        for (int i = number.length - 1; i >= 0; i--) {
            int nSum = number[i] + nTakeOver;
            if (i == number.length - 1) {
                nSum++;
            }
            if (nSum >= 10) {
                if (i == 0) {
                    isOverflow = true;
                } else {
                    nTakeOver = 1;
                    nSum = nSum - 10;
                    number[i] = nSum;
                }
            } else {
                number[i] = nSum;
                break;
            }
        }
        return isOverflow;
    }

    public void PrintNumber(int[] number) {   //该方法是负责打印一个正类，千万不要尝试将数组变成一个整数
        boolean isBeginning = true;
        for (int i = 0; i < number.length; i++) {
            if (isBeginning && number[i] != 0) {
                isBeginning = false;
            }
            if (!isBeginning) {
                System.out.print(number[i]);
            }
        }
    }

    public void Test(int n) {     //打印从1到最大的n位整数
        if (n <= 0) {
            System.out.println("输入出错，请重新输入！");
        }
        int[] number = new int[n];
        while (!Increment(number)) {
            PrintNumber(number);
        }
    }

    public static void main(String[] args) {
        new PrintMaxOfNDigits().Test(2);
    }

}
