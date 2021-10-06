package sessionkey.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sessionkey.SecuredRSAUsage;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;

public class TestPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea txtEncryptedSessionKey;
	private JTextField txtPrivateKey;
	private JTextArea txtDecryptedSessionKey;
	private JCheckBox cbxURLDecode;

	/**
	 * Create the panel.
	 */
	public TestPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {821, 0};
		gridBagLayout.rowHeights = new int[] {30, 30, 150, 20, 30, 20, 23, 150, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblEncryptedSessionKey = new JLabel("Encrypted Session Key (base64)");
		GridBagConstraints gbc_lblEncryptedSessionKey = new GridBagConstraints();
		gbc_lblEncryptedSessionKey.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEncryptedSessionKey.anchor = GridBagConstraints.NORTH;
		gbc_lblEncryptedSessionKey.insets = new Insets(0, 0, 5, 0);
		gbc_lblEncryptedSessionKey.gridx = 0;
		gbc_lblEncryptedSessionKey.gridy = 0;
		add(lblEncryptedSessionKey, gbc_lblEncryptedSessionKey);
		
		cbxURLDecode = new JCheckBox("URL Decode Session Key");
		cbxURLDecode.setSelected(true);
		GridBagConstraints gbc_cbxURLDecode = new GridBagConstraints();
		gbc_cbxURLDecode.anchor = GridBagConstraints.NORTHWEST;
		gbc_cbxURLDecode.insets = new Insets(0, 0, 5, 0);
		gbc_cbxURLDecode.gridx = 0;
		gbc_cbxURLDecode.gridy = 1;
		add(cbxURLDecode, gbc_cbxURLDecode);
		
		txtEncryptedSessionKey = new JTextArea();
		txtEncryptedSessionKey.setLineWrap(true);
		GridBagConstraints gbc_txtEncryptedSessionKey = new GridBagConstraints();
		gbc_txtEncryptedSessionKey.fill = GridBagConstraints.BOTH;
		gbc_txtEncryptedSessionKey.insets = new Insets(0, 0, 5, 0);
		gbc_txtEncryptedSessionKey.gridx = 0;
		gbc_txtEncryptedSessionKey.gridy = 2;
		add(txtEncryptedSessionKey, gbc_txtEncryptedSessionKey);
		txtEncryptedSessionKey.setColumns(10);
		
		JLabel lblPrivateKey = new JLabel("MEAP Private Key PEM file (pkcs8)");
		GridBagConstraints gbc_lblPrivateKey = new GridBagConstraints();
		gbc_lblPrivateKey.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPrivateKey.anchor = GridBagConstraints.NORTH;
		gbc_lblPrivateKey.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrivateKey.gridx = 0;
		gbc_lblPrivateKey.gridy = 3;
		add(lblPrivateKey, gbc_lblPrivateKey);
		
		txtPrivateKey = new JTextField();
		GridBagConstraints gbc_txtPrivateKey = new GridBagConstraints();
		gbc_txtPrivateKey.anchor = GridBagConstraints.NORTH;
		gbc_txtPrivateKey.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrivateKey.insets = new Insets(0, 0, 5, 0);
		gbc_txtPrivateKey.gridx = 0;
		gbc_txtPrivateKey.gridy = 4;
		add(txtPrivateKey, gbc_txtPrivateKey);
		txtPrivateKey.setColumns(10);
		
		JButton btnDecryptSessionKey = new JButton("Decrypt Session Key");
		btnDecryptSessionKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					generatePayload();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}) ;
		GridBagConstraints gbc_btnDecryptSessionKey = new GridBagConstraints();
		gbc_btnDecryptSessionKey.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnDecryptSessionKey.insets = new Insets(0, 0, 5, 0);
		gbc_btnDecryptSessionKey.gridx = 0;
		gbc_btnDecryptSessionKey.gridy = 5;
		add(btnDecryptSessionKey, gbc_btnDecryptSessionKey);
		
		JLabel lblDecryptedSessionKey = new JLabel("Decrypted Session Key (base64)");
		GridBagConstraints gbc_lblDecryptedSessionKey = new GridBagConstraints();
		gbc_lblDecryptedSessionKey.anchor = GridBagConstraints.NORTH;
		gbc_lblDecryptedSessionKey.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDecryptedSessionKey.insets = new Insets(0, 0, 5, 0);
		gbc_lblDecryptedSessionKey.gridx = 0;
		gbc_lblDecryptedSessionKey.gridy = 6;
		add(lblDecryptedSessionKey, gbc_lblDecryptedSessionKey);
		
		txtDecryptedSessionKey = new JTextArea();
		txtDecryptedSessionKey.setEditable(false);
		GridBagConstraints gbc_txtDecryptedSessionKey = new GridBagConstraints();
		gbc_txtDecryptedSessionKey.fill = GridBagConstraints.BOTH;
		gbc_txtDecryptedSessionKey.gridx = 0;
		gbc_txtDecryptedSessionKey.gridy = 7;
		add(txtDecryptedSessionKey, gbc_txtDecryptedSessionKey);

	}
	
	private void generatePayload() throws Exception {
		String sEncryptedSessionKey = txtEncryptedSessionKey.getText();
		String sPrivateKeyPEM = txtPrivateKey.getText();
		String sDecryptedSessionKey = "";
		boolean bURLDecode = false;

		bURLDecode = cbxURLDecode.isSelected();
		txtDecryptedSessionKey.setText("");

		if (bURLDecode) {
			sEncryptedSessionKey = URLDecoder.decode(sEncryptedSessionKey, StandardCharsets.UTF_8);
		}
		
//		System.out.println(Base64.getEncoder().encodeToString(aKey));
		
		sDecryptedSessionKey = SecuredRSAUsage.decryptPayload(sEncryptedSessionKey, sPrivateKeyPEM);
		sDecryptedSessionKey = URLEncoder.encode(sDecryptedSessionKey, StandardCharsets.UTF_8);
		
		txtDecryptedSessionKey.setText(sDecryptedSessionKey);
		txtDecryptedSessionKey.setLineWrap(true);
		txtDecryptedSessionKey.setWrapStyleWord(true);
	
		
	}
	
}
