import jxl.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by zhangqiao on 2018/12/27.
 */
public class projectCheck {
    public static void main(String[] args) throws IOException {
        //distinction("E://data.xls");
        //double number_two_answer = number_two();
        //System.out.println("K段神经元覆盖率为："+number_two_answer);//0.36533
        //traverse_data(0);
        //System.out.println(isIn(1.8696005,0.185164824,-4.6258817,4.6323595,50));
        System.out.println(number_nine("E://data.xls",50,40,30));
    }
    public static void distinction(String url) throws IOException {

        File file = new File(url);

        Sheet[] sheets;
        Workbook book;
        Cell one_cell;
        try {
            book = Workbook.getWorkbook(file);
            sheets = book.getSheets();

            for (Sheet sheet : sheets) {
                int rows = sheet.getRows();//当前工作表行数
                int columns = sheet.getColumns();//当前工作表列数
                //
                /*for(int i = 0; i < rows;i++){
                    for(int j = 0; j < columns;j++){
                        one_cell = sheet.getCell(j,i);
                        if(one_cell.getType() == CellType.NUMBER){
                            NumberCell numberCellOne = (NumberCell)one_cell;
                            System.out.println(numberCellOne.getValue());
                        }
                        //BigDecimal n = new BigDecimal(one_cell.getContents());


                    }
                }*/
                /*第一题*/
                int num = 0;

                for (int i = 0;i < columns; i++) {
                    one_cell = sheet.getCell(i,0);
                    if(one_cell.getType() == CellType.NUMBER){
                        NumberCell numberCellOne = (NumberCell)one_cell;
                        if(numberCellOne.getValue() > 0){
                            num++;
                        }
                    }
                }
                String result = "";
                float num_result =(float)num/columns;

                DecimalFormat df = new DecimalFormat("0.0");

                result = df.format(num_result);
                System.out.println("神经元覆盖率为"+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double number_two(){
        File file = new File("E://课程设计//边界值.xls");

        Sheet[] sheets;
        Workbook book;
        try {
            book = Workbook.getWorkbook(file);
            sheets = book.getSheets();
            Cell two_cell ;
            for (Sheet sheet : sheets) {
                int rows = sheet.getRows();//当前工作表行数
                int columns = sheet.getColumns();//当前工作表列数

                int sumResult = 0;
                for(int i = 0; i < columns;i++){
                    double colSum = 0.0;
                    double weighs[] = {0.0,0.0};
                    for(int j = 0; j < rows;j++){
                        double n = 0.0;
                        two_cell = sheet.getCell(i,j);
                        if(two_cell.getType() == CellType.NUMBER){
                            NumberCell numberCellOne = (NumberCell)two_cell;
                            n = numberCellOne.getValue();
                            colSum -= n;
                            weighs[j] = n;
                            if(colSum < 0){
                                colSum = Math.abs(colSum);
                            }
                        }
                    }
                    double average_n= colSum/50;
                    //此处获得每个神经元50等分之后的值，接下来就是遍历每个神经元
                    //又需要对data表进行操作 此处调用一个函数
                    int per_col_result = traverse_data(i,average_n,weighs);
                    sumResult += per_col_result;
                }
                System.out.println("sumResult是"+sumResult);
                //double k_n_coverage = sumResult/(50*columns);
                //
                String result = "";
                double num_result = (double) sumResult/(50*columns);

                DecimalFormat df = new DecimalFormat("0.00000");

                result = df.format(num_result);
                return  Double.parseDouble(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //遍历data
    public static int traverse_data(int col,double average_n,double weights[]){
        //System.out.println(average_n);
        /*for (int i =0;i < weights.length;i++){
            System.out.println("this is a weigh"+i+":"+weights[i]);
        }*/
        File file = new File("E://data.xls");

        Sheet[] sheets;
        Workbook book;
        Cell one_cell;
        Set<Double> set = new TreeSet<>();
        try {
            book = Workbook.getWorkbook(file);
            sheets = book.getSheets();
            //int count = 0;
            //double result[] = {0.0};
            for (Sheet sheet : sheets) {
                int rows = sheet.getRows();//当前工作表行数
                int columns = sheet.getColumns();//当前工作表列数
                //
                if(col <= columns){
                    for(int i = 0; i < rows;i++){
                       // for(int j = 0; j < columns;j++){
                            one_cell = sheet.getCell(col,i);//列在前行在后
                            if(one_cell.getType() == CellType.NUMBER){
                                NumberCell numberCellOne = (NumberCell)one_cell;
                                double testResult = isIn(numberCellOne.getValue(),average_n,weights[1],weights[0],50);
                               // System.out.println("testResult"+testResult+"i是"+col);
                                set.add(testResult);
                                /*if(i == 0){
                                    result[0] = testResult;
                                }else {
                                    boolean flag = true;
                                    for(int n = 0;n < result.length;n++){
                                        if(result[n] == testResult){
                                            flag = false;
                                        }
                                    }
                                    if (flag){
                                        count ++;
                                        result[result.length-1] = testResult;
                                    }
                                }*/
                            }
                        //}
                    }
                }else {
                    break;
                }
                //System.out.println("count是"+count);
                return set.size() ;
                //return  count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    //判断某个权值是否在某区间内
    public static double isIn(double testNumber,double average,double lNumber,double rNumber,int N){
        for(int i = 0;i < N;i++){
            if(testNumber == lNumber ||testNumber == rNumber){
                if(testNumber == lNumber){
                    return lNumber;
                }else {
                    return lNumber+average*(N-1);
                }
            }else {
                if(testNumber > lNumber && testNumber <= lNumber+average){
                    return lNumber;
                }else {
                    lNumber += average;
                }
            }
        }
        return -1;
    }


    //第九题
    //参数：工作表url  第一层神经元个数  第二层神经元个数  第三层神经元个数
    public static double number_nine(String url,int floor1,int floor2,int floor3){
        File file = new File(url);

        Sheet[] sheets;
        Workbook book;
        Cell two_cell;
        int count = 0;
        Set set1_2 = new TreeSet();
        Set set2_3 = new TreeSet();

        try {
            book = Workbook.getWorkbook(file);
            sheets = book.getSheets();

            List<List> line_all = new ArrayList();

            for (Sheet sheet : sheets) {
                int rows = sheet.getRows();//当前工作表行数
                int columns = sheet.getColumns();//当前工作表列数
                int j = 0;
                for(int i = 0; i < rows; i++){
                    List<Double> line = new ArrayList();
                    double n2 = 0.0;

                    for(int k = 0;k < columns;k++){
                        two_cell = sheet.getCell(k,i);
                        if(two_cell.getType() == CellType.NUMBER) {
                            NumberCell numberCellTwo = (NumberCell) two_cell;
                            n2 = numberCellTwo.getValue();
                        }
                        line.add(n2);
                    }
                    line_all.add(line);
                }
            }
            for(int i = 0; i < line_all.size();i++){
                List<Double> x2 = null;
                List<Double> x1 = line_all.get(i);
                for(int j = i+1; j < line_all.size();j++){
                    if(j == i && i <line_all.size()-1){
                        j++;
                    }
                    if(i == line_all.size()-1){
                        if(j == line_all.size()-1){
                            break;
                        }
                    }
                   x2 = line_all.get(j);
                    boolean flag1_12 = interval_cn_nochange(x1.subList(0,floor1-1),x2.subList(0,floor1-1));
                    List<Integer> flag1_3 = decision_neuron_change(x1.subList(floor1,floor1+floor2-1),x2.subList(floor1,floor1+floor2-1));

                    boolean flag2_12 = interval_cn_nochange(x1.subList(floor1,floor1+floor2-1),x2.subList(floor1,floor1+floor2-1));
                    List<Integer> flag2_3 = decision_neuron_change(x1.subList(floor1+floor2,floor1+floor2+floor3-1),x2.subList(floor1+floor2,floor1+floor2+floor3-1));
                    if(flag1_12){
                        if(flag1_3.size() > 0){
                            for (int n:flag1_3) {
                                set1_2.add(n);
                            }
                        }
                    }
                    if(flag2_12){
                        if(flag2_3.size() > 0){
                            for (int m:flag2_3) {
                                set2_3.add(m);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        count = floor1*set1_2.size() + floor2*set2_3.size();
        String result = "";
        double num_result = (double) count/(floor1*floor2+floor2*floor3);

        DecimalFormat df = new DecimalFormat("0.00000");

        result = df.format(num_result);
        return  Double.parseDouble(result);
    }

    //条件一：存在两个输入X1,X2，当输入分别为X1和X2时，前一层的神经元距离足够小。
    public  static boolean interval_cn_nochange(List<Double> x1,List<Double> x2){
        boolean flag = false;
        double ab_sum = 0.0;

        for(int i = 0; i < x1.size();i++){
            double n1 = x1.get(i);
            double n2 = x2.get(i);

            double m1 = n1-n2;
            //条件二：当输入为X1，X2时，条件神经元的这一层所有神经元的符号均未改变。
            //若不满足条件二直接退出
            if(((n1 > 0) && (n2 <= 0)) || ((n1 <= 0)&&(n2 > 0))){
                return  false;
            }
            if(m1 < 0){
                m1 = Math.abs(m1);
            }
            ab_sum += m1;
        }
        if(ab_sum <= 50){
           // System.out.println("sum是"+ab_sum);
            flag = true;
        }
        return  flag;
    }
    //条件三：当输入为X1,X2时，决策神经元的符号产生了改变。
    public static List<Integer> decision_neuron_change(List<Double> x1,List<Double> x2){
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < x2.size();i++){
            double n1 = x1.get(i);
            double n2 = x2.get(i);
            if(((n1 > 0) && (n2 <= 0)) || ((n1 <= 0)&&(n2 > 0))){
               list.add(i);
            }
        }
        return list;
    }
}
