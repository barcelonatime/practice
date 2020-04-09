package im.practice.sort;

public class SortMain {
    public static void main(String[] args) {
        int[] arr={32,28,9,13,78,55,12,300,98};
        long start=System.nanoTime();
        //bubbleSort(arr);
        //chooseSort(arr);
        insertSort(arr);
        long end =System.nanoTime();
        System.out.println("耗时："+(end-start));
        for (int a:arr
        ) {
            System.out.println(a);
        }
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 冒泡排序
     * @return
     */
     public static int[] bubbleSort(int[] arr){
         int j=arr.length-1;
         boolean flag=false;
         for (int i=0;i<=j;){
             for (int n=0;n<j;n++){
                 if (arr[n]>arr[n+1]){
                     int temp=arr[n];
                     arr[n]=arr[n+1];
                     arr[n+1]=temp;
                     flag=true;
                 }
             }
             if (!flag){
                 break;
             }
             j--;
         }
        return arr;
     }

    /**
     * 选择排序
     * @param arr
     * @return
     */
     public static int[] chooseSort(int[] arr){
         int j=arr.length-1;
         for (int i=0;i<j;i++){
             int minIndex=i;
             for (int n=i;n<j;n++){
                 if (arr[minIndex]>arr[n+1]){
                     minIndex=n+1;
                 }
             }
             int temp=arr[i];
             arr[i]=arr[minIndex];
             arr[minIndex]=temp;

         }
         return arr;
     }

    /**
     * 插入排序
     * @param arr
     * @return
     */
     public static int[] insertSort(int[] arr){
         int j=arr.length-1;
         for (int i=1;i<=j;i++){
             int temp=arr[i];
             int index=0;
             for(int n=i-1;n>=0;n--){
                 if (temp<arr[n]){
                     arr[n+1]=arr[n];
                 }else {
                    index=n+1;
                    break;
                 }
             }
             arr[index]=temp;
         }
         return arr;
     }


}
