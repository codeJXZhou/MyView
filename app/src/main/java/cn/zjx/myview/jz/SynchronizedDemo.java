package cn.zjx.myview.jz;

/**
 * @author zjx on 2018/9/5.
 */
public class SynchronizedDemo implements Runnable {
    private static int count = 0;

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            Thread thread = new Thread(new SynchronizedDemo());
//            thread.start();
//        }
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("---------->>result: " + Arrays.toString(small(new int[]{2, 1, 3, 4, 6, 5, 9, 8})));
//        int[] temp = new int[]{9, 1, 3, 4, 6, 5, 9, 8};
//        sort5(temp,0,7);
//        sort6(temp, 0, temp.length - 1);
//        System.out.println("---------->>result: " + Arrays.toString(temp));
//        System.out.println("result: " + count);
//        System.out.println("---------result: " + sum(temp, 0));
//
//        Hashtable<String, Double> tb = new Hashtable<>();
//        tb.put("苹果", 1.52);
//        tb.put("香蕉", 2.52);
//        tb.put("梨", 4.52);
//        tb.put("西瓜", 5.52);
//        System.out.println("---------result: " + tb.get("西瓜"));
    }

    @Override
    public void run() {
        synchronized (SynchronizedDemo.class) {
            for (int i = 0; i < 1000000; i++) count++;
        }
    }

    public static int findMax(int[] array, int from) {
        if (from == array.length - 1) {
            return array[from];
        }
        return Math.max(array[from], findMax(array, from + 1));
    }

    public static int sum(int[] arr, int a) {
        if (a < arr.length - 1)
            return arr[a] + sum(arr, a + 1);
        else {
            return arr[a];
        }
    }

    /**
     * 冒泡
     */
    public static void sort1(int[] old) {
        for (int j = 0; j < old.length - 1; j++) {
            for (int i = 0; i < old.length - 1 - j; i++) {
                if (old[i + 1] <= old[i]) {
                    int temp = old[i + 1];
                    old[i + 1] = old[i];
                    old[i] = temp;
                }
            }
        }
    }

    /**
     * 选择
     */
    public static void sort2(int[] old) {
        for (int i = 0; i < old.length - 1; i++) {
            int k = i;
            for (int j = k + 1; j < old.length; j++) {
                if (old[j] < old[k]) {
                    k = j;
                }
            }
            if (k != i) {
                int temp = old[i];
                old[i] = old[k];
                old[k] = temp;
            }
        }
    }

    /**
     * 插入
     */
    public static void sort3(int[] old) {
        for (int i = 0; i < old.length - 1; i++) {
            int current = old[i + 1];
            int index = i;
            while (index >= 0 && current < old[index]) {
                old[index + 1] = old[index];
                index--;
            }
            old[index + 1] = current;
        }
    }

    /**
     * 希尔 {9, 1, 3, 4, 6, 5, 9, 8}
     */
    public static void sort4(int[] old) {
        int increment = old.length;//增量
        while (increment > 1)//最后在增量为1并且是执行了情况下停止。
        {
            increment = increment / 3 + 1;//根据公式
            for (int i = increment; i < old.length; i++)//从[0]开始，对相距增量步长的元素集合进行修改。
            {
                int key = old[i];
                //以下和直接插入排序类似。
                int j = i - increment;
                while (j >= 0) {
                    if (key < old[j]) {
                        int temp = old[j];
                        old[j] = key;
                        old[j + increment] = temp;
                    }
                    j = j - increment;
                }
            }
        }
    }

    /**
     * 归并
     */
    public static void sort5(int[] old, int l, int r) {
        if (l >= r) return;
        int mid = (l + r) / 2;
        //递归二分 将数组分为  [左,中],(中,右]
        sort5(old, l, mid);
        sort5(old, mid + 1, r);
        //归并排序
        int aux[] = new int[r - l + 1]; //这里弄一个要处理的数组副本 长度是 R-L+1
        for (int i = l; i <= r; i++)    //副本数组从 L 开始，所以与原数组存在一个 L 的偏移量
            aux[i - l] = old[i];
        int i = l, j = mid + 1;        //i记录左边元素的下标位置 j记录右边元素的下标位置
        for (int k = l; k <= r; k++) { //k记录 arr 的下标位置
            if (i > mid) {
                old[k] = aux[j - l];
                j++;
            } else if (j > r) {
                old[k] = aux[i - l];
                i++;
            } else if (aux[i - l] < aux[j - l]) {
                old[k] = aux[i - l];
                i++;
            } else {
                old[k] = aux[j - l];
                j++;
            }
        }
    }

    /**
     * 快速
     */
    public static void sort6(int num[], int left, int right) {
        if (left < right) {
            int index = partition(num, left, right); //算出枢轴值
            sort6(num, left, index - 1);       //对低子表递归排序
            sort6(num, index + 1, right);        //对高子表递归排序
        }
    }

    /**
     * 调用partition(num,left,right)时，对num[]做划分，
     * 并返回基准记录的位置
     *
     * @return
     */
    public static int partition(int[] array, int lo, int hi) {
        /** 固定的切分方式 */
        int key = array[lo];//选取了基准点
        while (lo < hi) {
            //从后半部分向前扫描
            while (array[hi] >= key && hi > lo) {
                hi--;
            }
            array[lo] = array[hi];
            //从前半部分向后扫描
            while (array[lo] <= key && hi > lo) {
                lo++;
            }
            array[hi] = array[lo];
        }
        array[hi] = key;//最后把基准存入
        return hi;
    }

    /**
     * 堆
     */
    public static void sort7(int[] old) {

    }

    /**
     * 计数
     */
    public static void sort8(int[] old) {

    }

    /**
     * 桶
     */
    public static void sort9(int[] old) {

    }

    /**
     * 基数
     */
    public static void sort10(int[] old) {


    }


    public static int[] small(int[] oldArr) {
        int[] newArr = new int[oldArr.length];
        int[] tempArr = oldArr;
        int a = 0;
        int index = 0;
        for (int j = 0; j < oldArr.length; j++) {
            index = s(tempArr)[0];
            newArr[a] = s(tempArr)[1];
            tempArr = delete(index, tempArr);
            a++;
        }

        return newArr;
    }


    public static int[] s(int[] arr) {
        int[] small = new int[2];
        small[0] = 0;
        small[1] = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < small[1]) {
                small[0] = i;
                small[1] = arr[i];
            }
        }
        return small;
    }

    public static int[] delete(int index, int array[]) {
        //数组的删除其实就是覆盖前一位
        int[] arrNew = new int[array.length - 1];
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        System.arraycopy(array, 0, arrNew, 0, arrNew.length);
        return arrNew;
    }





}


