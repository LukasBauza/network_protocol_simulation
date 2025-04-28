import javax.swing.*;
import java.awt.*;

public class InfoField extends JPanel {
    private JLabel label;
    private JTextField textField;

    InfoField(String labelText, String textFieldText) {
        super.setLayout(new GridLayout(1, 2));
        super.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        this.label = new JLabel(labelText);
        this.label.setHorizontalAlignment(SwingConstants.LEFT);
        super.add(label);

        this.textField = new JTextField(textFieldText);
        super.add(textField);
    }

    InfoField(String labelTest) {
        super.setLayout(new GridLayout(1, 2));
        super.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        this.label = new JLabel(labelTest);
        this.label.setHorizontalAlignment(SwingConstants.LEFT);
        super.add(label);
    }

    public void setLabelText(String labelText) {
        this.label.setText(labelText);
        updateComponents();
    }

    public void setTextFieldText(String textFieldText) {
        this.textField.setText(textFieldText);
        updateComponents();
    }

    public void setEditable(boolean editable) {
        this.textField.setEditable(editable);
    }

    public JLabel getLabel() {
        return label;
    }

    public JTextField getTextField() {
        return textField;
    }

    private void updateComponents() {
        super.removeAll();
        super.add(label);
        super.add(textField);
    }

}
