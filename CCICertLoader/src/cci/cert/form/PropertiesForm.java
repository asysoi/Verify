package cci.cert.form;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cci.cert.certificate.CCICertLoader;
import cci.cert.certificate.CCIProperty;
import cci.cert.certificate.Config;
import cci.cert.certificate.ConfigReader;
import cci.cert.util.DesEncrypter;


public class PropertiesForm extends Dialog {

	static final Logger LOG = Logger.getLogger(PropertiesForm.class);
	private Object result;
	private Shell shlOtrs;
	private Button btnDelete;
	private Button btnZip;
	private Text txtURL;
	private Text txtRepoPath;
	private Label lblFtpLogin;
	private Text txtLogin;
	private Label lblFtpPassword;
	private Text txtPassword;
	private Label label;
	private Text txtFiles;
	private Label label_1;
	private Text txtDelay;
	private Text txtFilePath;
	
	
	
	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public PropertiesForm(Shell parent, int style) {
		super(parent, style);
		setText("OTRS Login");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		Display display = getParent().getDisplay();
		shlOtrs.setLocation((display.getClientArea().width - 330) / 2,
				(display.getClientArea().height - 210) / 2);
		
		lblFtpLogin = new Label(shlOtrs, SWT.NONE);
		lblFtpLogin.setText("FTP login");
		lblFtpLogin.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		lblFtpLogin.setBounds(20, 37, 75, 16);
		
		txtLogin = new Text(shlOtrs, SWT.BORDER);
		txtLogin.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		txtLogin.setBounds(124, 34, 294, 19);
		
		lblFtpPassword = new Label(shlOtrs, SWT.NONE);
		lblFtpPassword.setText("FTP Password");
		lblFtpPassword.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		lblFtpPassword.setBounds(20, 62, 98, 16);
		
		txtPassword = new Text(shlOtrs, SWT.BORDER);
		txtPassword.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		txtPassword.setBounds(124, 59, 294, 19);
		
		label = new Label(shlOtrs, SWT.NONE);
		label.setText("\u041B\u0438\u043C\u0438\u0442 \u0444\u0430\u0439\u043B\u043E\u0432");
		label.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		label.setBounds(20, 87, 98, 16);
		
		txtFiles = new Text(shlOtrs, SWT.BORDER);
		txtFiles.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		txtFiles.setBounds(124, 84, 143, 19);
		
		label_1 = new Label(shlOtrs, SWT.NONE);
		label_1.setText("\u0417\u0430\u0434\u0435\u0440\u0436\u043A\u0430");
		label_1.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		label_1.setBounds(20, 112, 98, 16);
		
		txtDelay = new Text(shlOtrs, SWT.BORDER);
		txtDelay.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		txtDelay.setBounds(124, 109, 143, 19);
		shlOtrs.open();
		shlOtrs.layout();
		shlOtrs.setFocus();
		
		loadProps();
		

		while (!shlOtrs.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlOtrs = new Shell(getParent(), SWT.BORDER | SWT.TITLE);
		shlOtrs.setText("\u041F\u0430\u0440\u0430\u043C\u0435\u0442\u0440\u044B \u0437\u0430\u0433\u0440\u0443\u0437\u043A\u0438");
		shlOtrs.setSize(439, 250);
		
		Label lblFtpServer = new Label(shlOtrs, SWT.NONE);
		lblFtpServer.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		lblFtpServer.setBounds(20, 12, 75, 16);
		lblFtpServer.setText("FTP Server");
		
		txtURL = new Text(shlOtrs, SWT.BORDER);
		txtURL.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		txtURL.setBounds(124, 10, 294, 19);

		btnDelete = new Button(shlOtrs, SWT.CHECK);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnDelete.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		btnDelete.setBounds(274, 87, 149, 16);
		btnDelete.setText("\u0423\u0434\u0430\u043B\u044F\u0442\u044C \u0444\u0430\u0439\u043B\u044B \u0441 FTP");
		
		
		btnZip = new Button(shlOtrs, SWT.CHECK);
		btnZip.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		btnZip.setBounds(274, 112, 149, 16);
		btnZip.setText("Грузить из ZIP");
		
		Label labelPath = new Label(shlOtrs, SWT.NONE);
		labelPath.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		labelPath.setBounds(20, 135, 107, 21);
		labelPath.setText("\u0420\u0435\u043F\u043E\u0437\u0438\u0442\u043E\u0440\u0438\u0439");
		
		txtRepoPath = new Text(shlOtrs, SWT.BORDER);
		txtRepoPath.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		txtRepoPath.setBounds(124, 135, 267, 21);
		
		Button btnPath = new Button(shlOtrs, SWT.NONE);
		btnPath.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				DirectoryDialog fd = new DirectoryDialog(shlOtrs, SWT.OPEN);
				fd.setText("Путь к репозиторию хранения файлов сертификатов");
				fd.setFilterPath(txtRepoPath.getText());
				String selected = fd.open();
				System.out.println(selected);

				if (selected != null && !selected.isEmpty()) {
					txtRepoPath.setText(selected);
				}
			}
		});
		btnPath.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		btnPath.setBounds(392, 135, 26, 21);
		btnPath.setText("...");

		Label labelFilesPath = new Label(shlOtrs, SWT.NONE);
		labelFilesPath.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		labelFilesPath.setBounds(20, 159, 107, 21);
		labelFilesPath.setText("Путь к XML");
		
		txtFilePath = new Text(shlOtrs, SWT.BORDER);
		txtFilePath.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		txtFilePath.setBounds(124, 162, 267, 21);
		
		Button btnFiles = new Button(shlOtrs, SWT.NONE);
		btnFiles.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				DirectoryDialog fd = new DirectoryDialog(shlOtrs, SWT.OPEN);
				fd.setText("Путь к каталогу файлов сертификатов");
				fd.setFilterPath(txtFilePath.getText());
				String selected = fd.open();
				System.out.println(selected);

				if (selected != null && !selected.isEmpty()) {
					txtFilePath.setText(selected);
				}
			}
		});
		btnFiles.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		btnFiles.setBounds(392, 162, 26, 21);
		btnFiles.setText("...");		

		Button btnSave = new Button(shlOtrs, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveProps();
				shlOtrs.close();
			}
		});
		btnSave.setText("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		btnSave.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		btnSave.setBounds(262, 188, 75, 25);

		Button btnCancel = new Button(shlOtrs, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
                shlOtrs.close();
			}
		});
		btnCancel.setText("\u041E\u0442\u043C\u0435\u043D\u0430");
		btnCancel.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		btnCancel.setBounds(343, 188, 75, 25);
	}

	private void saveProps() {
		
		CCIProperty props = CCIProperty.getInstance();
			
		props.setProperty(Config.ISDELETE, btnDelete.getSelection() ? "true" : "false");
		props.setProperty(Config.ISZIP, btnZip.getSelection() ? "true" : "false");
		props.setProperty(Config.URL, txtURL.getText());
		props.setProperty(Config.LOGIN, txtLogin.getText());
		props.setProperty(Config.PSW, txtPassword.getText());
		props.setProperty(Config.FILES, txtFiles.getText());
		props.setProperty(Config.DELAY, txtDelay.getText());
		props.setProperty(Config.REPPATH, txtRepoPath.getText());
		props.setProperty(Config.XMLPATH, txtFilePath.getText());
		
	
		try {
				DesEncrypter encrypter = new DesEncrypter();
				if (props.getProperty(Config.PSW) != null) {
					props.setProperty(Config.PSW,
							encrypter.encrypt(props.getProperty(Config.PSW)));
				
					ConfigReader.getInstance().storeConfig(
						CCIProperty.getInstance().getProperties());
				
					props.setProperty(Config.PSW,
							encrypter.decrypt(props.getProperty(Config.PSW)));
				} else {
					ConfigReader.getInstance().storeConfig(
							CCIProperty.getInstance().getProperties());
				}
		} catch (Exception ex) {
			LOG.error("Config saving. " + ex.getMessage());;
		}
	
	}
	
	private void loadProps() {
		CCIProperty props = CCIProperty.getInstance();
		
		txtURL.setText((String) props.getProperty(Config.URL)!= null ? props.getProperty(Config.URL) : "");
		btnDelete.setSelection(props.getProperty(Config.ISDELETE)!= null ? Boolean.parseBoolean(props.getProperty(Config.ISDELETE)) : true);
		btnZip.setSelection(props.getProperty(Config.ISZIP)!= null ? Boolean.parseBoolean(props.getProperty(Config.ISZIP)) : true);
		txtLogin.setText(props.getProperty(Config.LOGIN) != null ? props.getProperty(Config.LOGIN) : "");
		txtPassword.setText(props.getProperty(Config.PSW) != null ? props.getProperty(Config.PSW) : "");
		txtFiles.setText(props.getProperty(Config.FILES) != null ? props.getProperty(Config.FILES) : "");
		txtDelay.setText(props.getProperty(Config.DELAY) != null ? props.getProperty(Config.DELAY) : "");
		txtRepoPath.setText(props.getProperty(Config.REPPATH) != null ? props.getProperty(Config.REPPATH) : "");
		txtFilePath.setText(props.getProperty(Config.XMLPATH) != null ? props.getProperty(Config.XMLPATH) : "");
		
		LOG.info("Load config properties");
	}
}
