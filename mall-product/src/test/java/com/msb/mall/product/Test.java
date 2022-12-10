package com.msb.mall.product;

public class Test {
    public static void main(String[] args) {
        sort(new int[]{2,1,3,4},0,3);

    }
    static void sort(int[] nums,int left,int right){
        int index = left+1;
        boolean exchange = true;
        while(index< right && exchange){
            exchange = false;
            for (int i = right; i >= index; i--) {
                if (nums[i - 1] > nums[i]){
                    swap(nums,i-1,i);
                    exchange=true;
                }
            }
            index++;
        }
    }
    static void swap(int[] nums,int i1, int i2){
        int temp = nums[i1];
        nums[i1] = nums[i2];
        nums[i2] = temp;
    }
}
