package gui;

import configuration.ConfigurationElement;
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
public class AlgorithmTestDialog extends JDialog {


    private final String mergeSortName = "Sortowanie przez scalanie";
    private final String simpleJoinSortName = "Sortowanie przez lączenie proste";
    private final String naturalJoinSortName = "Sortowanie przez lączenie naturalne";
    private final String polyphaseSortName = "Sortowanie wielofazowe";
    private final String basicVariant = "Podstawowy";
    private final String mergeSort2WayNFilesVariant = "scalanie dwukierunkowe - N pomocniczych plików";
    private final String mergeSort2Way4FilesVariant = "scalanie dwukierunkowe - 4 pliki pomocnicze";
    private final String mergeSortKWayVariant = "scalanie k-kierunkowe";
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
    private MainWindow mainWindow;

    public AlgorithmTestDialog(String title, MainWindow mainWindow) {
        setTitle(title);
        this.mainWindow = mainWindow;
        setContentPane(rootPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        polesInit();
        pack();
        setVisible(true);

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
                        algorithmParameter = (Integer) memorySpinner.getValue() * 1000 * 1000;
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

                    ConfigurationElement element = new ConfigurationElement(className, mainWindow.getSourceFileTextField().getText(),
                            sourceFileTextField.getText(), inputBufferSize, outputBufferSize, algorithmParameter);
                    mainWindow.addConfigurationElement(element);
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
                    variantComboBox.addItem(mergeSortKWayVariant);
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
    }

    private void polesInit() {
        algorithmComboBox.addItem(simpleJoinSortName);
        algorithmComboBox.addItem(naturalJoinSortName);
        algorithmComboBox.addItem(mergeSortName);
        algorithmComboBox.addItem(polyphaseSortName);
        variantComboBox.addItem(basicVariant);
        inputBufferSizeSpinner.setPreferredSize(new Dimension(70, 21));
        outputBufferSizeSpinner.setPreferredSize(new Dimension(70, 21));
        memorySpinner.setPreferredSize(new Dimension(70, 21));
        inputBufferSizeSpinner.setModel(new SpinnerNumberModel(8, 8, Integer.MAX_VALUE / 1024, 1));
        outputBufferSizeSpinner.setModel(new SpinnerNumberModel(8, 8, Integer.MAX_VALUE / 1024, 1));
        memorySpinner.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
        memorySpinner.setEnabled(false);

    }

    private boolean dataCheck() {
        boolean completeData = true;
        if (sourceFileTextField.getText().isEmpty())
            completeData = false;
        return completeData;
    }


}
