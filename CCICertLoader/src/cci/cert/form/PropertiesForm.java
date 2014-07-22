package cci.cert.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class PropertiesForm extends Dialog {

	private Object result;
	private Shell shlOtrs;
	private Button button_0;
	private Button button_2;
	private Button button_4;
	private Button button_5;
	private Button button_1;
	private Text txtTime;
	private Text txtUrl;
	private Text txtPath;
	
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
		shlOtrs.open();
		shlOtrs.layout();
		shlOtrs.setFocus();

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
		shlOtrs.setText("OTRS \u043F\u0430\u0440\u0430\u043C\u0435\u0442\u0440\u044B");
		shlOtrs.setSize(439, 289);
		
		Label label = new Label(shlOtrs, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		label.setBounds(20, 12, 341, 16);
		label.setText("\u041F\u0435\u0440\u0438\u043E\u0434\u0438\u0447\u043D\u043E\u0441\u0442\u044C \u043F\u0440\u043E\u0432\u0435\u0440\u043A\u0438 \u043D\u0430\u043B\u0438\u0447\u0438\u044F \u043D\u043E\u0432\u044B\u0445 \u0437\u0430\u044F\u0432\u043E\u043A(\u043C\u0438\u043D\u0443\u0442\u044B)");
		
		txtTime = new Text(shlOtrs, SWT.BORDER);
		txtTime.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		txtTime.setBounds(371, 10, 47, 19);
		
		button_0 = new Button(shlOtrs, SWT.CHECK);
		button_0.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		button_0.setBounds(20, 34, 196, 16);
		button_0.setText("\u0421\u043E\u0445\u0440\u0430\u043D\u044F\u0442\u044C \u043F\u0430\u0440\u0430\u043C\u0435\u0442\u0440\u044B \u043B\u043E\u0433\u0438\u043D\u0430");

		button_1 = new Button(shlOtrs, SWT.CHECK);
		button_1.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		button_1.setBounds(20, 56, 196, 16);
		button_1.setText("\u0410\u0432\u0442\u043E\u043C\u0430\u0442\u0438\u0447\u0435\u0441\u043A\u0438\u0439 \u043B\u043E\u0433\u0438\u043D");

		button_2 = new Button(shlOtrs, SWT.CHECK);
		button_2.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		button_2.setBounds(20, 78, 233, 16);
		button_2.setText("\u0423\u0432\u0435\u0434\u043E\u043C\u043B\u044F\u0442\u044C \u043E \u043A\u0430\u0436\u0434\u043E\u0439 \u043D\u043E\u0432\u043E\u0439 \u0437\u0430\u044F\u0432\u043A\u0435");

		button_4 = new Button(shlOtrs, SWT.CHECK);
		button_4.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		button_4.setBounds(20, 100, 121, 16);
		button_4.setText("\u0417\u0432\u0443\u043A\u043E\u0432\u043E\u0439 \u0441\u0438\u0433\u043D\u0430\u043B");

		button_5 = new Button(shlOtrs, SWT.CHECK);
		button_5.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		button_5.setBounds(20, 122, 294, 16);
		button_5.setText("\u0422\u0440\u0435\u0431\u043E\u0432\u0430\u0442\u044C \u043F\u043E\u0434\u0442\u0432\u0435\u0440\u0436\u0434\u0435\u043D\u0438\u0435 \u0434\u043B\u044F \u0437\u0430\u043A\u0440\u044B\u0442\u0438\u044F \u043E\u043A\u043D\u0430");

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
		btnSave.setBounds(262, 221, 75, 25);

		Button btnCancel = new Button(shlOtrs, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
                shlOtrs.close();
			}
		});
		btnCancel.setText("\u041E\u0442\u043C\u0435\u043D\u0430");
		btnCancel.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		btnCancel.setBounds(343, 221, 75, 25);
		
		Label lblOtrsUrl = new Label(shlOtrs, SWT.NONE);
		lblOtrsUrl.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		lblOtrsUrl.setBounds(20, 156, 64, 16);
		lblOtrsUrl.setText("OTRS URL");
		
		Label labelPath = new Label(shlOtrs, SWT.NONE);
		labelPath.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		labelPath.setBounds(20, 184, 107, 21);
		labelPath.setText("\u041F\u0443\u0442\u044C \u043A \u0431\u0440\u0430\u0443\u0437\u0435\u0440\u0443");
		
		txtUrl = new Text(shlOtrs, SWT.BORDER);
		txtUrl.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		txtUrl.setBounds(90, 154, 329, 21);
		
		txtPath = new Text(shlOtrs, SWT.BORDER);
		txtPath.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		txtPath.setBounds(124, 184, 267, 21);
		
		Button btnPath = new Button(shlOtrs, SWT.NONE);
		btnPath.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shlOtrs, SWT.OPEN);
				fd.setText("Путь к броузеру");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.exe", "*.*" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				System.out.println(selected);

				if (selected != null && !selected.isEmpty()) {
					txtPath.setText(selected);
				}
			}
		});
		btnPath.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		btnPath.setBounds(392, 184, 26, 21);
		btnPath.setText("...");
		
		loadProps();
	}

	private void saveProps() {
		
		CCIProperty props = CCIProperty.getInstance();
				
		props.setProperty(OTRS.PR_LOGIN_SAVE, button_0.getSelection() ? "true" : "false");
		props.setProperty(OTRS.PR_AUTOLOGIN, button_1.getSelection() ? "true" : "false");;
		props.setProperty(OTRS.PR_REMINDER, button_2.getSelection() ? "true" : "false");;
		props.setProperty(OTRS.PR_SOUND, button_4.getSelection()  ? "true" : "false");;
		props.setProperty(OTRS.PR_CONFIRMATION, button_5.getSelection()  ? "true" : "false");;
		props.setProperty(OTRS.PR_CHECK_TIME, txtTime.getText());
		props.setProperty(OTRS.PR_OTRS_URL, txtUrl.getText());
		props.setProperty(OTRS.PR_PATH, txtPath.getText());
		
		try {
			
				DesEncrypter encrypter = new DesEncrypter();
				if (props.getProperty(OTRS.PR_PSW) != null) {
					props.setProperty(OTRS.PR_PSW,
							encrypter.encrypt(props.getProperty(OTRS.PR_PSW)));
				
					ConfigReader.getInstance().storeConfig(
						CCIProperty.getInstance().getProperties());
				
					props.setProperty(OTRS.PR_PSW,
							encrypter.decrypt(props.getProperty(OTRS.PR_PSW)));
				} else {
					ConfigReader.getInstance().storeConfig(
							CCIProperty.getInstance().getProperties());
				}
		} catch (Exception ex) {
			OTRSApplicationAWT.LOG.error("Config saving. " + ex.getMessage());;
		}
	}
	
	private void loadProps() {
		CCIProperty props = CCIProperty.getInstance();
		txtTime.setText((String) props.getProperty(OTRS.PR_CHECK_TIME)!= null ? props.getProperty(OTRS.PR_CHECK_TIME) : "5"); 
		button_0.setSelection(props.getProperty(OTRS.PR_LOGIN_SAVE)!= null ? Boolean.parseBoolean(props.getProperty(OTRS.PR_LOGIN_SAVE)) : true );
		button_1.setSelection(props.getProperty(OTRS.PR_AUTOLOGIN)!= null ? Boolean.parseBoolean(props.getProperty(OTRS.PR_AUTOLOGIN)) : true );
		button_2.setSelection(props.getProperty(OTRS.PR_REMINDER)!= null ? Boolean.parseBoolean(props.getProperty(OTRS.PR_REMINDER)) : true);
		button_4.setSelection(props.getProperty(OTRS.PR_SOUND)!= null ? Boolean.parseBoolean(props.getProperty(OTRS.PR_SOUND)) : false);
		button_5.setSelection(props.getProperty(OTRS.PR_CONFIRMATION)!= null ? Boolean.parseBoolean(props.getProperty(OTRS.PR_CONFIRMATION)) : true);
		txtUrl.setText(props.getProperty(OTRS.PR_OTRS_URL) != null ? props.getProperty(OTRS.PR_OTRS_URL) : "");
		txtPath.setText(props.getProperty(OTRS.PR_PATH) != null ? props.getProperty(OTRS.PR_PATH) : "");
		OTRSApplicationAWT.LOG.info("Load config properties");
		
	}
}
