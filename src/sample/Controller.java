package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Клас-контролер інтерфейсу
 * @author Nastya Baturkina
 */
public class Controller {

    static public ObservableList<String> list = FXCollections.observableArrayList("Чоловіча", "Жіноча");
    ArrayList<String> listCoeff = new ArrayList<>();

    @FXML
    public TextArea textArea;
    @FXML
    private TextField age;
    @FXML
    private ComboBox<String> sex;
    @FXML
    private TextField bmi;
    @FXML
    private TextField dp;
    @FXML
    private TextField sp;
    @FXML
    private TextField esrB;
    @FXML
    private TextField plaB;
    @FXML
    private TextField leuB;
    @FXML
    private TextField eryB;
    @FXML
    private TextField hemB;
    @FXML
    private TextField bcB;
    @FXML
    private TextField hyd;
    @FXML
    private TextField cyc;
    @FXML
    private TextField esrE;
    @FXML
    private TextField plaE;
    @FXML
    private TextField leuE;
    @FXML
    private TextField eryE;
    @FXML
    private TextField bcE;
    @FXML
    private TextField hydMax;
    @FXML
    private TextField hydMin;
    @FXML
    private TextField cycMax;
    @FXML
    private TextField cycMin;
    @FXML
    private TextField esrMax;
    @FXML
    private TextField esrMin;
    @FXML
    private TextField plaMax;
    @FXML
    private TextField plaMin;
    @FXML
    private TextField leuMax;
    @FXML
    private TextField leuMin;
    @FXML
    private TextField bcMax;
    @FXML
    private TextField bcMin;
    @FXML
    private Label bcLabel;
    @FXML
    private Label eryLabel;
    @FXML
    private Label plaLabel;
    @FXML
    private Label label0;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;

    /**
     * Метод  для ініціалізації інтерфейсу
     */
    @FXML
    public void initialize() {
        sex.setItems(list);
    }

    /**
     * Метод для очищення полів з вхідними даними пацієнта
     */
    @FXML
    public void resetVariables() {
        age.clear();
        bmi.clear();
        dp.clear();
        sp.clear();
        esrB.clear();
        plaB.clear();
        leuB.clear();
        eryB.clear();
        hemB.clear();
        bcB.clear();
    }

    /**
     * Метод для встановлення значень обмежень за замовчуванням
     */
    @FXML
    public void restrictionsByDefault() {
        hydMax.setText("20");
        hydMin.setText("10");
        cycMax.setText("400");
        cycMin.setText("200");
        esrMax.setText("15");
        esrMin.setText("5");
        leuMax.setText("90");
        leuMin.setText("40");
        bcMax.setText("15");
        bcMin.setText("0");
        plaMax.setText("360");
        plaMin.setText("180");
    }

    /**
     * Метод для очищення полів обмежень
     */
    @FXML
    public void resetRestrictions() {
        hydMax.clear();
        hydMin.clear();
        cycMax.clear();
        cycMin.clear();
        esrMax.clear();
        esrMin.clear();
        plaMax.clear();
        plaMin.clear();
        leuMax.clear();
        leuMin.clear();
        bcMax.clear();
        bcMin.clear();
    }

    /**
     * Функція для завантаження даних пацієнта
     */
    public void loadB(javafx.event.ActionEvent actionEvent) {
        try {
            String fileName = "patientDef.txt";
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

            String line;
            Random random = new Random();
            int rand = random.nextInt(4);
            int i = 0;

            while((line = br.readLine())  != null)
            {
                if(i == rand)
                {
                    break;
                }
                i++;
            }
            String strArr[] = line.split("\t");

            age.setText(strArr[0]);
            if(strArr[1].equals("1"))
            {
                sex.setValue(list.get(0));
            }
            else
                {
                    sex.setValue(list.get(1));
                }

            bmi.setText(strArr[2]);
            dp.setText(strArr[3]);
            sp.setText(strArr[4]);
            esrB.setText(strArr[5]);
            plaB.setText(strArr[6]);
            leuB.setText(strArr[7]);
            eryB.setText(strArr[8]);
            hemB.setText(strArr[9]);
            bcB.setText(strArr[10]);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Метод для обрахунку результату
     */
    @FXML
    public void result() {
        Double x1 = Double.parseDouble(age.getText());
        Double x2 = 0.0;
        if (sex.getValue() == "Чоловіча") {
            x2 = 1.0;
        }
        if (sex.getValue() == "Жіноча") {
            x2 = 2.0;
        }
        Double x3 = Double.parseDouble(bmi.getText());
        Double x4 = Double.parseDouble(dp.getText());
        Double x5 = Double.parseDouble(sp.getText());
        Double x6 = Double.parseDouble(esrB.getText());
        Double x7 = Double.parseDouble(plaB.getText());
        Double x8 = Double.parseDouble(leuB.getText());
        Double x9 = Double.parseDouble(eryB.getText());
        Double x10 = Double.parseDouble(hemB.getText());
        Double x11 = Double.parseDouble(bcB.getText());

        /**
         * Коефіцієнти за замовчуванням
         */

        /**
         * Коефійієнти моделі X12
         */
        Double a00 = 1.918;
        Double a01 = -42.866;
        Double a02 = 2.002;
        Double a03 = 5.836;
        Double a04 = -1.247;
        Double a05 = 1.747;
        Double a06 = -2.024;
        Double a07 = 142.984;
        Double a08 = 43.427;
        Double const_0 = -133.221;

        /**
         * Коефійієнти моделі X13
         */
        Double a10 = 14.982;
        Double a11 = 12.572;
        Double a12 = -27.052;
        Double a13 = -6.375;
        Double a14 = -1.32;
        Double a15 = -7.021;
        Double a16 = 1.199;
        Double a17 = 12.014;
        Double a18 = 19.681;
        Double a19 = 25.229;
        Double const_1 = -406.544;

        /**
         * Коефійієнти моделі X14
         */
        Double a20 = -3.111;
        Double a21 = -0.704;
        Double a22 = -4.254;
        Double a23 = 7.369;
        Double a24 = 11.901;
        Double a25 = 2.873;
        Double a26 = 18.142;
        Double a27 = 2.358;
        Double a28 = -2.098;
        Double const_2 = -128.817;

        /**
         * Коефійієнти моделі X15
         */
        Double a30 = -125.178;
        Double a31 = 8.864;
        Double a32 = 13.765;//try to delete?
        Double a33 = -25.858;
        Double a34 = 3.877;
        Double a35 = -0.289;
        Double a36 = 4.624;
        Double a37 = 0.309;
        Double const_3 = -5.580;

        /**
         * Коефійієнти моделі X16
         */
        Double a40 = -6.960;
        Double a41 = 14.149;
        Double a42 = 0.526;
        Double a43 = 0.156;
        Double a44 = -10.073;
        Double a45 = -2.015;
        Double a46 = -0.765;
        Double a47 = 27.397;
        Double a48 = -4.876;
        Double const_4 = 351.695;

        Double x12Coeff1 = a00 + a01 / (x3 * x9);
        Double x12Coeff2 = a02 / (Math.pow(x3, 2));
        Double x12Const = const_0 + a03 * x2 + a04 * x3 + a05 * x4 + a06 * x11 + (a07 * x11) / x4 + a08 / x9;

        Double x13Coeff1 = (a10 * x1 + a11 * x4 + a12 * x8 + a13 * x11) / x7;
        Double x13Coeff2 = (a14 * x3) / x5;
        Double x13Const = const_1 + a15 * x4 + a16 * x6 + a17 * x8 + (a18 * x3) / x9 + (a19 * x5) / x3;

        Double x14Coeff1 = (a20 * x1) / x10 + (a21 * x7) / x3 + (a22 * x10 + a23 * x11) / x6;
        Double x14Coeff2 = a24 / x4 + a25 / (x3 * x9);
        Double x14Const = const_2 + a26 * x2 + a27 * x10 + a28 * x11;

        Double x15Coeff1 = a30 / (x5 * x7) + a31 / (x9 * x10);
        Double x15Coeff2 = a32 / (x1 * x6) + a33 / (x5 * x6);
        Double x15Const = const_3 + (a34 * x1) / x8 + (a35 * x5) / x8 + (a36 * x8) / x1 + (a37 * x10) / x7;

        Double x16Coeff1 = (a40 * x1) / x4 + (a41 * x7) / x11;
        Double x16Coeff2 = (a42 * x6) / x10 + (a43 * x7) / x11 + a44 / (x7 * x9);
        Double x16Const = const_4 + (a45 * x4) / x9 + (a46 * x5) / x9 + (a47 * x10) / x6 + a48 * x7 * x9;

        Double x12Max = Double.parseDouble(esrMax.getText());
        Double x12Ub = x12Max - x12Const;
        Double x12Min = Double.parseDouble(esrMin.getText());
        Double x12Lb = x12Min - x12Const;

        Double x13Max = Double.parseDouble(plaMax.getText());
        Double x13Ub = x13Max - x13Const;
        Double x13Min = Double.parseDouble(plaMin.getText());
        Double x13Lb = x13Min - x13Const;

        Double x14Max = Double.parseDouble(leuMax.getText());
        Double x14Ub = x14Max - x14Const;
        Double x14Min = Double.parseDouble(leuMin.getText());
        Double x14Lb = x14Min - x14Const;

        Double x16Max = Double.parseDouble(bcMax.getText());
        Double x16Ub = x16Max - x16Const;
        Double x16Min = Double.parseDouble(bcMin.getText());
        Double x16Lb = x16Min - x16Const;

        Double u1Lb = Double.parseDouble(hydMin.getText());
        Double u1Ub = Double.parseDouble(hydMax.getText());
        Double u2Lb = Double.parseDouble(cycMin.getText());
        Double u2Ub = Double.parseDouble(cycMax.getText());

        /**
         * Рішення задачі максимізації еритроцитів
         */
        LinearProgram lp = new LinearProgram(new double[]{x15Coeff1, x15Coeff2});
        lp.addConstraint(new LinearSmallerThanEqualsConstraint(new double[]{x12Coeff1, x12Coeff2}, x12Ub, "c1"));
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(new double[]{x12Coeff1, x12Coeff2}, x12Lb, "c2"));
        lp.addConstraint(new LinearSmallerThanEqualsConstraint(new double[]{x13Coeff1, x13Coeff2}, x13Ub, "c3"));
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(new double[]{x13Coeff1, x13Coeff2}, x13Lb, "c4"));
        lp.addConstraint(new LinearSmallerThanEqualsConstraint(new double[]{x14Coeff1, x14Coeff2}, x14Ub, "c5"));
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(new double[]{x14Coeff1, x14Coeff2}, x14Lb, "c6"));
        lp.addConstraint(new LinearSmallerThanEqualsConstraint(new double[]{x16Coeff1, x16Coeff2}, x16Ub, "c7"));
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(new double[]{x16Coeff1, x16Coeff2}, x16Lb, "c8"));

        lp.addConstraint(new LinearBiggerThanEqualsConstraint(new double[]{1.0, 0.0}, u1Lb, "c9"));
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(new double[]{0.0, 1.0}, u2Lb, "c10"));
        lp.addConstraint(new LinearSmallerThanEqualsConstraint(new double[]{1.0, 0.0}, u1Ub, "c11"));
        lp.addConstraint(new LinearSmallerThanEqualsConstraint(new double[]{0.0, 1.0}, u2Ub, "c12"));
        lp.setMinProblem(false);
        LinearProgramSolver solver = SolverFactory.newDefault();
        double[] sol = solver.solve(lp);

        bcLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
        plaLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
        eryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        if (sol[0] >= u1Lb && sol[0] <= u1Ub && sol[1] >= u2Lb && sol[1] <= u2Ub) {
            int u1 = (int) sol[0];
            int u2 = (int) sol[1];
            hyd.setText(Integer.toString(u1));
            cyc.setText(Integer.toString(u2));
            double x16 = x16Coeff1 * u1 + x16Coeff2 * u2 + x16Const;
            if (x16 < 0.0) {
                x16 = 0.0;
            }
            double x12 = x12Coeff1 * u1 + x12Coeff2 * u2 + x12Const;
            if (x12 < 0.0) {
                x12 = 0.0;
            }
            double x13 = x13Coeff1 * u1 + x13Coeff2 * u2 + x13Const;
            if (x13 < 0.0) {
                x13 = 0.0;
            }
            double x14 = x14Coeff1 * u1 + x14Coeff2 * u2 + x14Const;
            if (x14 < 0.0) {
                x14 = 0.0;
            }
            double x15 = x15Coeff1 * u1 + x15Coeff2 * u2 + x15Const;
            if (x15 < 0.0) {
                x15 = 0.0;
            }
            if ((int) x12 > x12Max) {
                label0.setText(" > норми!");
                label0.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            } else {
                if ((int) x12 < x12Min) {
                    label0.setText(" < норми!");
                    label0.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                } else {
                    label0.setText("");
                }
            }
            if ((int) x13 > x13Max) {
                label1.setText(" > норми!");
                label1.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            } else {
                if ((int) x13 < x13Min) {
                    label1.setText(" < норми!");
                    label1.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                } else {
                    label1.setText("");
                }
            }
            if ((int) x14 > x14Max) {
                label2.setText(" > норми!");
                label2.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            } else {
                if ((int) x14 < x14Min) {
                    label2.setText(" < норми!");
                    label2.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                } else {
                    label2.setText("");
                }
            }
            if ((int) x16 > x16Max) {
                label4.setText(" > норми!");
                label4.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            } else {
                if ((int) x16 < x16Min) {
                    label4.setText(" < норми!");
                    label4.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                } else {
                    label4.setText("");
                }
            }
            esrE.setText(Integer.toString((int) x12));
            plaE.setText(Integer.toString((int) x13));
            leuE.setText(Integer.toString((int) x14));
            String formattedDouble = new DecimalFormat("#0.00").format(x15);
            eryE.setText(formattedDouble);
            bcE.setText(Integer.toString((int) x16));
            //label0.setText("");
            //label1.setText("");
            //label2.setText("");
            //label3.setText("");
            //label4.setText("");

        } else {
            ArrayList<Double> values = new ArrayList<>();
            double x15_1 = x15Coeff1 * u1Lb + x15Coeff2 * u2Lb + x15Const;
            values.add(x15_1);
            double x15_2 = x15Coeff1 * u1Ub + x15Coeff2 * u2Ub + x15Const;
            values.add(x15_2);
            double x15_3 = x15Coeff1 * u1Lb + x15Coeff2 * u2Ub + x15Const;
            values.add(x15_3);
            double x15_4 = x15Coeff1 * u1Ub + x15Coeff2 * u2Lb + x15Const;
            values.add(x15_4);
            int index = values.indexOf(Collections.max(values));
            double maxValue = values.get(index);
            Double u1;
            Double u2;
            if (x15_1 == maxValue) {
                u1 = u1Lb;
                u2 = u2Lb;
            } else {
                if (x15_2 == maxValue) {
                    u1 = u1Ub;
                    u2 = u2Ub;
                } else {
                    if (x15_3 == maxValue) {
                        u1 = u1Lb;
                        u2 = u2Ub;
                    } else {
                        u1 = u1Ub;
                        u2 = u2Lb;
                    }
                }
            }
            double x15 = x15Coeff1 * u1 + x15Coeff2 * u2 + x15Const;
            if (x15 < 0.0) {
                x15 = 0.0;
            }
            label1.setText("");
            double x12 = x12Coeff1 * u1 + x12Coeff2 * u2 + x12Const;
            if (x12 < 0.0) {
                x12 = 0.0;
            }
            if ((int) x12 > x12Max) {
                label0.setText(" > норми!");
            } else {
                if ((int) x12 < x12Min) {
                    label0.setText(" < норми!");
                } else {
                    label0.setText("");
                }
            }
            double x13 = x13Coeff1 * u1 + x13Coeff2 * u2 + x13Const;
            if (x13 < 0.0) {
                x13 = 0.0;
            }
            if ((int) x13 > x13Max) {
                label1.setText(" > норми!");
            } else {
                if ((int) x13 < x13Min) {
                    label1.setText(" < норми!");
                } else {
                    label1.setText("");
                }
            }
            double x14 = x14Coeff1 * u1 + x14Coeff2 * u2 + x14Const;
            if (x14 < 0.0) {
                x14 = 0.0;
            }
            if ((int) x14 > x14Max) {
                label2.setText(" > норми!");
            } else {
                if ((int) x14 < x14Min) {
                    label2.setText(" < норми!");
                } else {
                    label2.setText("");
                }
            }
            double x16 = x16Coeff1 * u1 + x16Coeff2 * u2 + x16Const;
            if (x16 < 0.0) {
                x16 = 0.0;
            }
            if ((int) x16 > x16Max) {
                label4.setText(" > норми!");
            } else {
                if ((int) x16 < x16Min) {
                    label4.setText(" < норми!");
                } else {
                    label4.setText("");
                }
            }
            hyd.setText(Double.toString(u1));
            cyc.setText(Double.toString(u2));
            esrE.setText(Integer.toString((int) x12));
            plaE.setText(Integer.toString((int) x13));
            leuE.setText(Integer.toString((int) x14));
            String formattedDouble = new DecimalFormat("#0.00").format(x15);
            eryE.setText(formattedDouble);
            bcE.setText(Integer.toString((int) x16));
        }

        /**
         * Для проміжних моделей
         */
        listCoeff.add(Double.toString(x12Coeff1));
        listCoeff.add(Double.toString(x12Coeff2));
        listCoeff.add(Double.toString(x12Const));

        listCoeff.add(Double.toString(x13Coeff1));
        listCoeff.add(Double.toString(x13Coeff2));
        listCoeff.add(Double.toString(x13Const));

        listCoeff.add(Double.toString(x14Coeff1));
        listCoeff.add(Double.toString(x14Coeff2));
        listCoeff.add(Double.toString(x14Const));

        listCoeff.add(Double.toString(Math.abs(x15Coeff1)));
        listCoeff.add(Double.toString(Math.abs(x15Coeff1)));
        listCoeff.add(Double.toString(x15Const));

        listCoeff.add(Double.toString(x16Coeff1));
        listCoeff.add(Double.toString(x16Coeff2));
        listCoeff.add(Double.toString(x16Const));

        int k = 12;
        for (int i = 0; i < 15; ) {
            textArea.appendText("X" + k + " = U1 * " + listCoeff.get(i) + " + U2 * " + listCoeff.get(i + 1) + " + " + listCoeff.get(i + 2) + "\n\n");
            i += 3;
            k += 1;
        }
        textArea.appendText("______________________________________________________________________________________________________________________\n");
    }

}


