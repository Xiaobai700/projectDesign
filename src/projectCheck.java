import com.sun.org.apache.bcel.internal.generic.LNEG;
import jxl.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by zhangqiao on 2018/12/27.
 */
public class projectCheck {
    public static void main(String[] args) throws IOException {
        //distinction("E://data.xls");
        double number_two_answer = number_two();
        System.out.println("K段神经元覆盖率为："+number_two_answer);//0.9
        //traverse_data(0);
        //System.out.println(isIn(1.8696005,0.185164824,-4.6258817,4.6323595,50));
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
             /*第二题*/

             /*第九题*/
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
                            /*if(numberCellOne.getValue() < 0){
                                 n = Math.abs(numberCellOne.getValue());//取绝对值
                            }else {
                                 n = numberCellOne.getValue();
                            }*/
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
                float num_result =(float)sumResult/(50*columns);

                DecimalFormat df = new DecimalFormat("0.0");

                result = df.format(num_result);
                System.out.println("神经元覆盖率为"+result);
                //
                return  Double.parseDouble(result);
            }
             /*第二题*/
             /*第九题*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int traverse_data(int col,double average_n,double weights[]){
        //System.out.println(average_n);
        /*for (int i =0;i < weights.length;i++){
            System.out.println("this is a weigh"+i+":"+weights[i]);
        }*/
        File file = new File("E://data.xls");

        Sheet[] sheets;
        Workbook book;
        Cell one_cell;
        try {
            book = Workbook.getWorkbook(file);
            sheets = book.getSheets();
            int count = 0;
            double result[] = {0.0};
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
                                System.out.println("testResult"+testResult+"i是"+col);
                                if(i == 0){
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
                                }
                            }
                        //}
                    }
                }else {
                    break;
                }
                System.out.println("count是"+count);
                return count;
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
}
