package tools;

public interface Mark_Point {
    default int markself(int[] result) { //根据result内的各属性打分(自己)
        int sum = 0;
        sum += result[0] * 100000;
        sum += result[1] * 100000;
        sum += result[2] * 128;
        sum += result[3] * 31;
        sum += result[4] * 0;
        sum += result[5] * 1024;
        sum += result[6] * 512;
        sum += result[7] * 0;
        sum += result[8] * 256;
        sum += result[9] * 32;
        sum += result[10] * 31;
        sum += result[11] * 0;
        sum += result[12] * 16;
        sum += result[13] * 3;
        sum += result[14] * 0;
        return sum;
    }

    default int markenemy(int [] result) { //根据result内的各属性打分（堵人）
        int sum = 0;
        sum += result[0] * 30000;
        sum += result[1] * 30000;
        sum += result[2] * 15000;
        sum += result[3] * 12000;
        sum += result[4] * 0;
        sum += result[5] * 3000;
        sum += result[6] * 2000;
        sum += result[7] * 0;
        sum += result[8] * 256;
        sum += result[9] * 32;
        sum += result[10] * 31;
        sum += result[11] * 0;
        sum += result[12] * 16;
        sum += result[13] * 3;
        sum += result[14] * 0;
        return sum;
    }
}
