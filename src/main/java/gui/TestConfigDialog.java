package gui;

import algorithms.megresort.MergeSorter;
import algorithms.naturaljoin.NaturalJoinSorter;
import algorithms.polyphasesort.PolyphaseSorter;
import algorithms.simplejoin.SimpleJoinSorter;
import configuration.BenchmarkConfigurationElement;
import configuration.DebugConfigurationElement;
import configuration.benchmarks.MergeSortBenchmark;
import configuration.benchmarks.NaturalJoinBenchmark;
import configuration.benchmarks.PolyphaseSortBenchmark;
import configuration.benchmarks.SimpleJoinBenchmark;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Witold on 2015-12-20.
 */
public class TestConfigDialog extends JDialog {


    private final String mergeSortName = "Sortowanie przez scalanie";
    private final String simpleJoinSortName = "Sortowanie przez lączenie proste";
    private final String naturalJoinSortName = "Sortowanie przez lączenie naturalne";
    private final String polyphaseSortName = "Sortowanie wielofazowe";
    private final String basicVariant = "Podstawowy";
    private final String mergeSort2WayNFilesVariant = "scalanie dwukierunkowe - N pomocniczych plików";
    private final String mergeSort2Way4FilesVariant = "scalanie dwukierunkowe - 4 pliki pomocnicze";
    //    private final String mergeSortKWayVariant = "scalanie k-kierunkowe";
    private JPanel rootPanel;
    private JComboBox algorithmComboBox;
    private JComboBox variantComboBox;
    private JPanel algorithm;
    private JPanel variant;
    private JPanel Buffers;
    private JPanel in;
    private JPanel out;
    private JSpinner inputBufferSizeSpinner;
    private JSpinner outputBufferSizeSpinner;
    private JPanel memory;
    private JSpinner memorySpinner;
    private JPanel fileName;
    private JPanel buttons;
    private JTextField sourceFileTextField;
    private JButton sourceFileButton;
    private JButton cancelButton;
    private JButton addConfigItemButton;
    private JPanel mainPanel;
    private JCheckBox debugModeCheckBox;
    private JPanel iterations;
    private JPanel WarmUp;
    private JPanel It;
    private JSpinner warmUpSpinner;
    private JSpinner iterationsSpinner;
    private MainWindow mainWindow;

    public TestConfigDialog(String title, MainWindow mainWindow) {
        setTitle(title);
        this.mainWindow = mainWindow;
        setModal(true);
        setContentPane(rootPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        polesInit();
        pack();


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        addConfigItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO pamietac o parametrze k
                if (dataCheck()) {
                    String resultFile = sourceFileTextField.getText();
                    int inputBufferSize = (Integer) inputBufferSizeSpinner.getValue() * 1024;
                    int outputBufferSize = (Integer) outputBufferSizeSpinner.getValue() * 1024;
                    int algorithmParameter = 0;
                    if (memorySpinner.isEnabled()) {
                        algorithmParameter = (Integer) memorySpinner.getValue() * 1000 * 1024 / 4;
                    }
                    String className;
                    switch (algorithmComboBox.getSelectedIndex()) {
                        case 0:
                            className = SimpleJoinBenchmark.class.getSimpleName();
                            break;
                        case 1:
                            className = NaturalJoinBenchmark.class.getSimpleName();
                            break;
                        case 2:
                            className = MergeSortBenchmark.class.getSimpleName();
                            break;
                        case 3:
                            className = PolyphaseSortBenchmark.class.getSimpleName();
                            break;
                        default:
                            className = SimpleJoinBenchmark.class.getSimpleName();
                            break;
                    }
                    if (debugModeCheckBox.isSelected()) {
                        className = getDebugClassName();
                        String methodName = getVariantType(className);
                        int[] algorithmParameters = null;
                        if (algorithmParameter != 0) {
                            algorithmParameters = new int[1];
                            algorithmParameters[0] = algorithmParameter;
                        }
                        DebugConfigurationElement element = new DebugConfigurationElement(mainWindow.getSourceFileTextField().getText(),
                                resultFile, inputBufferSize, outputBufferSize, className, methodName, algorithmParameters);
                        mainWindow.addConfigurationElement(element);
                    } else {
                        int warmUp = (Integer) warmUpSpinner.getValue();
                        int measure = (Integer) iterationsSpinner.getValue();
                        BenchmarkConfigurationElement element = new BenchmarkConfigurationElement(className, mainWindow.getSourceFileTextField().getText(),
                                sourceFileTextField.getText(), inputBufferSize, outputBufferSize, algorithmParameter, warmUp, measure);
                        mainWindow.addConfigurationElement(element);
                    }
                    setVisible(false);
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(rootPanel.getParent().getComponent(0), "Brak nazwy pliku wynikowego",
                            "Niekompletne dane", JOptionPane.WARNING_MESSAGE);

                }
            }
        });
        sourceFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Plik żródłowy");
                chooser.setDialogType(JFileChooser.FILES_ONLY);
                chooser.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));
                int returnValue = chooser.showDialog(rootPanel.getParent().getComponent(0), "Create");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    sourceFileTextField.setText(chooser.getSelectedFile().getAbsolutePath());
                }

            }
        });
        algorithmComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (algorithmComboBox.getSelectedIndex() == 2) {
                    variantComboBox.removeAllItems();
                    variantComboBox.addItem(mergeSort2Way4FilesVariant);
                    variantComboBox.addItem(mergeSort2WayNFilesVariant);
//                    variantComboBox.addItem(mergeSortKWayVariant);
                } else {
                    variantComboBox.removeAllItems();
                    variantComboBox.addItem(basicVariant);
                }
                if (algorithmComboBox.getSelectedIndex() >= 2)
                    memorySpinner.setEnabled(true);
                else
                    memorySpinner.setEnabled(false);
            }
        });
        debugModeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (debugModeCheckBox.isSelected()) {
                    variant.setVisible(true);
                    iterations.setVisible(false);
                } else {
                    variant.setVisible(false);
                    iterations.setVisible(true);
                }
            }
        });
        setVisible(true);
    }

    private void polesInit() {
        algorithmComboBox.addItem(simpleJoinSortName);
        algorithmComboBox.addItem(naturalJoinSortName);
        algorithmComboBox.addItem(mergeSortName);
        algorithmComboBox.addItem(polyphaseSortName);
        variantComboBox.addItem(basicVariant);
        variant.setVisible(false);
        Dimension spinnerDim = new Dimension(70, 21);
        inputBufferSizeSpinner.setPreferredSize(spinnerDim);
        outputBufferSizeSpinner.setPreferredSize(spinnerDim);
        memorySpinner.setPreferredSize(spinnerDim);
        iterationsSpinner.setPreferredSize(spinnerDim);
        warmUpSpinner.setPreferredSize(spinnerDim);
        inputBufferSizeSpinner.setModel(new SpinnerNumberModel(8, 8, Integer.MAX_VALUE / 1024, 1));
        outputBufferSizeSpinner.setModel(new SpinnerNumberModel(8, 8, Integer.MAX_VALUE / 1024, 1));
        memorySpinner.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
        warmUpSpinner.setModel(new SpinnerNumberModel(3, 0, 200, 1));
        iterationsSpinner.setModel(new SpinnerNumberModel(5, 1, 200, 1));
        memorySpinner.setEnabled(false);
    }

    private boolean dataCheck() {
        boolean completeData = true;
        if (sourceFileTextField.getText().isEmpty())
            completeData = false;
        return completeData;
    }

    private String getVariantType(String className) {
        if (className.compareTo(MergeSorter.class.getName()) == 0) {
            switch (variantComboBox.getSelectedIndex()) {
                case 0:
                    return "twoWayMergeSortFourFiles";
                case 1:
                    return "twoWayMergeSortNFiles";
                case 2:
                    return "kWayMergeSort";
                default:
                    return "twoWayMergeSortFourFiles";
            }
        } else
            return "sort";

    }

    private String getDebugClassName() {
        switch (algorithmComboBox.getSelectedIndex()) {
            case 0:
                return SimpleJoinSorter.class.getName();
            case 1:
                return NaturalJoinSorter.class.getName();
            case 2:
                return MergeSorter.class.getName();
            case 3:
                return PolyphaseSorter.class.getName();
            default:
                return SimpleJoinSorter.class.getName();
        }
    }


}
