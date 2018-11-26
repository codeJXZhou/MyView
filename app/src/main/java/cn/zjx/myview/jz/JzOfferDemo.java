package cn.zjx.myview.jz;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author zjx on 2018/9/11.
 */
public class JzOfferDemo {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("We are happy");
        System.out.println("---------result: " + replaceBlank(sb));

        int[] arr = {1, 3, 5, 7, 9, 11};
        int key = 3;
        int poi = twoPart(arr, key, 0, arr.length - 1);
        System.out.println("---------result: " + ((poi == -1) ? "无" : "有"));

        int[] arr1 = {3, 4, 9, 11, 1, 2};
        System.out.println("---------result: " + method1(arr1));

        System.out.println("---------result: " + Fibonacci(10));

        System.out.println("---------result: " + numberOf1(10));

        int[] arr2 = {2, 4, 6, 7, 9, 11};
        jiOu(arr2);
        System.out.println("---------result: " + Arrays.toString(arr2));

        int[] a = {1, 3, 5, 7, 9};
        int[] b = {2, 4, 6, 8, 10, 12};
        System.out.println("---------result: " + Arrays.toString(hebing(a, b)));

        int[][] circle = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        printCircle(circle);

        int[] num = {2, 4, 6, 8, 10, 12, 6, 6, 6, 6, 6};
        System.out.println("---------result: " + getNumber(num));

        int[] numK = {7, 6, 3, 10, 8, 5, 11};
        printK(numK, 4);

        int[] maxSum = {-1, 3, 4, -2, 9, -7, 8};
        System.out.println("---------result: " + printMaxNum(maxSum));

        System.out.println("---------result: " + getTimeOf1(100));

        int[] minInArr = {1, 12, 123};
        System.out.println("---------result: " + getMinInArr(minInArr));

        System.out.println("---------result: " + getUglyNum(6));

        String ss = "aabccdee";
        int index = FirstNotRepeatingChar(ss);
        System.out.println("---------result: " + ss.charAt(index));

        int[] timeInArr = {1, 2, 3, 3, 4, 4, 4, 6};
        System.out.println("---------result: " + timeInArr(timeInArr, 3));

        /*
         * System.arraycopy 复制数组
         */
        int[] fromArr = {1, 2, 3, 4, 5, 6};
        int[] toArr = new int[fromArr.length - 1];
        System.arraycopy(fromArr, 1, toArr, 0, fromArr.length - 1);
        System.out.println("---------result: " + Arrays.toString(toArr));

        int[] arrXY = {1, 3, 5, 7, 8, 9};
        getNumXY(arrXY, 10);

        System.out.println("---------result: " + leftReverse("abcdefg", 2));

        getPerCent(6);
    }


    /**
     * 替换空格
     */
    public static String replaceBlank(StringBuffer sb) {
        if (sb == null || sb.length() <= 0) {
            return "";
        }
        int blankCount = 0;
        //先统计出空格的数量
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == ' ') {
                ++blankCount;
            }
        }
        int oldLength = sb.length(); //得到原来字符串的长度
        int newLength = oldLength + blankCount * 2; //将空格替换为"%20"后的字符长度
        int oldIndex = oldLength - 1; //原来字符串的索引
        int newIndex = newLength - 1; //新字符串的索引
        sb.setLength(newLength);
        //一次遍历，替换其中的空格为"%20"
        while (oldIndex >= 0 && newIndex > oldIndex) {
            if (sb.charAt(oldIndex) == ' ') {
                sb.setCharAt(newIndex--, '0');
                sb.setCharAt(newIndex--, '2');
                sb.setCharAt(newIndex--, '%');
            } else {
                sb.setCharAt(newIndex--, sb.charAt(oldIndex));
            }
            --oldIndex;
        }
        return sb.toString();
    }


    /**
     * 二分查找
     */
    public static int twoPart(int[] arr, int key, int low, int high) {
        if (key < arr[low] || key > arr[high] || low > high) {
            return -1;
        }
        int mid = (low + high) / 2;
        if (arr[mid] > key) {
            return twoPart(arr, key, low, mid - 1);
        } else if (arr[mid] < key) {
            return twoPart(arr, key, mid + 1, high);
        } else {
            return mid;
        }
    }

    /**
     * 旋转数组的最小数字
     */
    public static int method1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int p1 = 0;
        int p2 = arr.length - 1;
        int min = arr[p1];
        int mid = 0;
        while (arr[p1] >= arr[p2]) {
            if (p2 - p1 == 1) {
                min = arr[p2];
                break;
            }
            mid = (p1 + p2) / 2;
            if (arr[p1] == arr[mid] && arr[p2] == arr[mid]) {
                min = method1_1(arr, p1, p2);
                break;
            }
            if (arr[p1] <= arr[mid]) {
                p1 = mid;
            }
            if (arr[p2] >= arr[mid]) {
                p2 = mid;
            }
        }
        return min;
    }

    public static int method1_1(int[] arr, int p1, int p2) {
        int min = arr[p1];
        for (int i = p1 + 1; i <= p2; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    /**
     * 斐波拉契数列
     */
    private static int Fibonacci(int n) {
        int[] result = new int[]{0, 1};
        if (n < 2) {
            return result[n];
        }
        int fib1 = 1;
        int fib2 = 0;
        int fib = 0;
        for (int i = 2; i <= n; i++) {
            fib = fib1 + fib2;
            fib2 = fib1;
            fib1 = fib;
        }
        return fib;
    }

    /**
     * 二进制中1的个数
     */
    private static int numberOf1(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }

    /**
     * 调整数组中，奇数在偶数前
     */
    private static void jiOu(int[] arr) {
        int lo = 0;
        int hi = arr.length - 1;
        while (lo < hi) {
            while (lo < hi && !isFunc(arr[lo])) {
                lo++;
            }
            while (lo < hi && isFunc(arr[hi])) {
                hi--;
            }
            if (lo < hi) {
                int temp = arr[lo];
                arr[lo] = arr[hi];
                arr[hi] = temp;
            }
        }
    }

    private static boolean isFunc(int arr) {
        return (arr & 1) == 0;
    }

    /**
     * 合并两个有序数组
     */
    private static int[] hebing(int[] a, int[] b) {
        int m = 0;
        int n = 0;
        int[] ab = new int[a.length + b.length];
        while (m < a.length && n < b.length) {
            if (a[m] <= b[n]) {
                ab[m + n] = a[m];
                m++;
            } else {
                ab[m + n] = b[n];
                n++;
            }
        }
        while (m < a.length) {
            ab[m + n] = a[m];
            m++;
        }
        while (n < b.length) {
            ab[m + n] = b[n];
            n++;
        }
        return ab;
    }


    /**
     * 顺时针打印矩形
     */
    private static void printCircle(int[][] arr) {
        if (arr == null || arr.length <= 0) {
            return;
        }
        int start = 0;
        while (arr[0].length > start * 2 && arr.length > start * 2) {
            print(arr, start);
            start++;
        }
        System.out.println("");
    }

    private static void print(int[][] arr, int start) {
        int column = arr[0].length;
        int row = arr.length;
        int endX = column - 1 - start;
        int endY = row - 1 - start;
        for (int i = start; i <= endX; i++) {
            int number = arr[start][i];
            System.out.print(number + ",");
        }
        if (start < endY) {
            for (int i = start + 1; i <= endY; i++) {
                int number = arr[i][endX];
                System.out.print(number + ",");
            }
        }
        if (start < endX && start < endY) {
            for (int i = endX - 1; i >= start; i--) {
                int number = arr[endY][i];
                System.out.print(number + ",");
            }
        }
        if (start < endX && start < endY - 1) {
            for (int i = endY - 1; i >= start + 1; i--) {
                int number = arr[i][start];
                System.out.print(number + ",");
            }
        }
    }

    /**
     * 数组中出次数超过一半的数字
     */
    private static int getNumber(int[] arr) {
        if (arr == null || arr.length <= 0) {
            return 0;
        }
        int result = arr[0];
        int time = 1;
        for (int i = 1; i < arr.length; i++) {
            if (time == 0) {
                result = arr[i];
                time = 1;
            } else if (arr[i] == result) {
                time++;
            } else {
                time--;
            }
        }
        if (!checkTimes(arr, result)) {
            return 0;
        }
        return result;
    }

    private static boolean checkTimes(int[] arr, int result) {
        boolean overHalf = false;
        int time = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == result) {
                time++;
            }
        }
        if (time > arr.length / 2) {
            overHalf = true;
        }
        return overHalf;
    }

    /**
     * 最小的k个数
     */
    private static void printK(int[] arr, int k) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        if (arr == null || arr.length <= 0) {
            System.out.println("不合法的数组");
        }
        if (k >= arr.length) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + ",");
            }
            System.out.println("");
        } else {

            for (int i = 0; i < arr.length; i++) {
                treeSet.add(arr[i]);
            }
            int a = 0;
            for (Integer integer : treeSet) {
                if (a < k) {
                    System.out.print(integer + ",");
                    a++;
                }
            }
            System.out.println("");
        }
    }


    /**
     * 连续子数组的最大和
     */
    private static int printMaxNum(int[] arr) {
        if (arr == null || arr.length <= 0) {
            return 0;
        }
        int result = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (sum <= 0) {
                sum = arr[i];
            } else {
                sum += arr[i];
            }
            if (sum > result) {
                result = sum;
            }
        }
        return result;
    }

    /**
     * 1到n，1的出现次数
     */
    private static int getTimeOf1(int n) {
        int time = 0;
        for (int i = 0; i <= n; i++) {
            time += NumberOf1(i);
        }
        return time;
    }

    private static int NumberOf1(int i) {
        int num = 0;
        while (i > 0) {
            if (i % 10 == 1) {
                num++;
            }
            i = i / 10;
        }
        return num;
    }

    /**
     * 将数组排成最小的数
     */
    private static String getMinInArr(int[] arr) {
        if (arr == null || arr.length <= 0) {
            return "空数组";
        }
        Integer[] num = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            num[i] = arr[i];
        }
        Arrays.sort(num, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return (o1 + "" + o2).compareTo(o2 + "" + o1);
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num.length; i++) {
            sb.append(num[i]);
        }
        return sb.toString();
    }


    /**
     * 求第n个丑数
     */
    private static int getUglyNum(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] arr = new int[n];
        arr[0] = 1;
        int num2 = 0;
        int num3 = 0;
        int num5 = 0;
        for (int i = 1; i < n; i++) {
            int min = getMin(arr[num2] * 2, arr[num3] * 3, arr[num5] * 5);
            arr[i] = min;
            while (arr[num2] * 2 == arr[i]) {
                num2++;
            }
            while (arr[num3] * 3 == arr[i]) {
                num3++;
            }
            while (arr[num5] * 5 == arr[i]) {
                num5++;
            }
        }
        return arr[n - 1];
    }

    private static int getMin(int number1, int number2, int number3) {
        int min = (number1 < number2) ? number1 : number2;
        return min < number3 ? min : number3;
    }

    /**
     * 第一个只出现一次的字符
     */
    public static int FirstNotRepeatingChar(String str) {
        int[] hash = new int[256];
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char temp = str.charAt(i);
            hash[temp]++;
        }
        int i;
        for (i = 0; i < len; i++) {
            char temp = str.charAt(i);
            if (hash[temp] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 数字在排序数组中出现的次数
     */
    private static int timeInArr(int[] arr, int n) {
        if (arr == null || arr.length <= 0) {
            return 0;
        }
        int first = getFirstIndex(arr, n, 0, arr.length - 1);
        int last = getLastIndex(arr, n, 0, arr.length - 1);
        int time = last - first + 1;
        return time;
    }

    private static int getFirstIndex(int[] arr, int n, int start, int end) {
        if (start > end) {
            return -1;
        }
        int index = (start + end) / 2;
        int data = arr[index];
        if (data == n) {
            if ((index > 0 && arr[index - 1] != n) || index == 0) {
                return index;
            } else {
                end = index - 1;
            }
        } else if (data > n) {
            end = index - 1;
        } else {
            start = index + 1;
        }
        return getFirstIndex(arr, n, start, end);
    }

    private static int getLastIndex(int[] arr, int n, int start, int end) {
        if (start > end) {
            return -1;
        }
        int index = (start + end) / 2;
        int data = arr[index];
        if (data == n) {
            if ((index < arr.length - 1 && arr[index + 1] != n) || index == arr.length - 1) {
                return index;
            } else {
                start = index + 1;
            }
        } else if (data < n) {

            start = index + 1;
        } else {
            end = index - 1;
        }
        return getLastIndex(arr, n, start, end);
    }


    /**
     * 在顺序数字中找出和为n的两个数字,任意一对
     */
    private static void getNumXY(int[] arr, int n) {
        if (arr == null || arr.length <= 0) {
            System.out.println("无效数组");
        }
        int start = 0;
        int end = arr.length - 1;
        while (end > start) {
            int sum = arr[start] + arr[end];
            if (sum == n) {
                System.out.println(arr[start] + "--" + arr[end]);
                break;
            } else if (sum > n) {
                end--;
            } else {
                start++;
            }
        }
    }

    private static String leftReverse(String ss, int n) {
        if (ss == null) {
            return null;
        }
        if (n >= ss.length()) {
            return ss;
        }
        String a = ss.substring(0, n);
        String b = ss.substring(n, ss.length() - 1);
        return b + a;
    }

    /**
     * n个骰子的点数
     */
    private static void getPerCent(int number) {
        if (number < 1) {
            return;
        }
        int g_maxValue = 6;
        int[][] probabilities = new int[2][];
        probabilities[0] = new int[g_maxValue * number + 1];
        probabilities[1] = new int[g_maxValue * number + 1];
        int flag = 0;
        for (int i = 1; i <= g_maxValue; i++) {
            probabilities[0][i] = 1;
        }
        for (int k = 2; k <= number; ++k) {
            for (int i = 0; i < k; ++i) {
                probabilities[1 - flag][i] = 0;
            }
            for (int i = k; i <= g_maxValue * k; ++i) {
                probabilities[1 - flag][i] = 0;
                for (int j = 1; j <= i && j <= g_maxValue; ++j) {
                    probabilities[1 - flag][i] += probabilities[flag][i - j];
                }
            }
            flag = 1 - flag;
        }
        double total = Math.pow(g_maxValue, number);
        for (int i = number; i <= g_maxValue * number; i++) {
            double ratio = (double) probabilities[flag][i] / total;
            System.out.println(i + ":" + ratio);
        }
    }

    /**
     * 1+2+...+n 库函数 n（n+1）/2 = (n^2+n)/2
     */
    public int sumNone(int n) {
        int sum = (int) (Math.pow(n, 2) + n);
        return sum >> 1;
    }

    /**
     * 不用加减乘除做加法
     */
    public int add(int num1, int num2) {
        int sum, carry;
        do {
            sum = num1 ^ num2;
            carry = (num1 & num2) << 1;
            num1 = sum;
            num2 = carry;
        } while (num2 != 0);
        return num1;
    }
}
