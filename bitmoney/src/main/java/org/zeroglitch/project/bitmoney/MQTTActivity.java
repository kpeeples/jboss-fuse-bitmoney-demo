package org.zeroglitch.project.bitmoney;

import java.io.StringReader;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MQTTActivity extends Activity implements OnClickListener, OnItemSelectedListener, OnKeyListener {
	
	// Region Information
	// 
	//  Bitcoin rate is 1 to 1
	//  So bitcoin is our default currency
	//  
	private final int apacCoinValue = 100;
	private final int naCoinValue = 200;
	private final int ltamCoinValue = 300;
	private final int emeaCoinValue = 150;
	
	private int myRegionValue = 0;
	

	private final String TAG = "MQTTClient";
	EditText addressET = null;
	EditText destinationET = null;
	EditText messageET = null;
	EditText receiveET = null;
	EditText userNameET = null;
	EditText passwordET = null;
	EditText userText = null;

	Button connectButton = null;
	Button disconnectButton = null;
	Button sendButton = null;
	Button receiveButton = null;
	Button prefSendButton = null;
	private Button buyButton;
	private Button sellButton;
	private Button transferButton;
	
	private TextView east;
	private TextView west;
	private TextView balance;
	
	private GridView gridView;
	private ArrayAdapter<String> adapter;

	private ProgressDialog progressDialog = null;

	String sAddress = null;
	String sUserName = null;
	String sPassword = null;
	String sDestination = null;
	String sMessage = null;

	MQTT mqtt = null;
	MQTT mqttPrivate = null;
	SharedPreferences preferences = null;
	CallbackConnection respConn;

	FutureConnection connection = null;
	FutureConnection privateConnection = null;

	String user = "wtjamiegmail.com";
	String callback = "";
	String region = "";
	String eastVal = "";
	String westVal = "";
	String balanceVal = "";
	private String result;
	private String userId;
	

	// Temp Messages
	StringBuffer regMessage = new StringBuffer();
	StringBuffer ratesMessage = new StringBuffer();
	StringBuffer currentMessage = null;
	private StringBuffer balanceMessage;
	private StringBuffer transactionMessage;
	private StringBuffer buyMessage;
	private StringBuffer sellMessage;
	private StringBuffer transferMessage;
	private String myRegion;
	private EditText userInput;
	private Spinner regionSpinner;
	private TextView valueDisplay;
	private String transferRegion;
	
	void setRegMessages() {
		regMessage = new StringBuffer();
		regMessage.append("<BitmoneyExchange>");
		regMessage.append("<Register>");
		regMessage.append("<Username>" + user + "</Username>");
		regMessage.append("<CallbackTopic>" + user + "</CallbackTopic>");
		regMessage.append("<Region>" + region + "</Region>");
		regMessage.append("</Register>");
		regMessage.append("</BitmoneyExchange>");

	}

	void setRatesMessage() {
		user = preferences.getString("user", "");
		ratesMessage = new StringBuffer();
		ratesMessage.append("<BitmoneyExchange>");
		ratesMessage.append("<Rates>");
		ratesMessage.append("<Username>" + user + "-rates</Username>");
		ratesMessage.append("</Rates>");
		ratesMessage.append("</BitmoneyExchange>");

	}

	void setBalanceMessage() {
		user = preferences.getString("user", "");
		balanceMessage = new StringBuffer();
		balanceMessage.append("<BitmoneyExchange>");
		balanceMessage.append("<Balance>");
		balanceMessage.append("<Username>" + user + "</Username>");
		balanceMessage.append("<Callback>" + user + "-balance</Callback>");
		balanceMessage.append("</Balance>");
		balanceMessage.append("</BitmoneyExchange>");

	}

	void setTransactionMessage() {
		user = preferences.getString("user", "");
		transactionMessage = new StringBuffer();
		transactionMessage.append("<BitmoneyExchange>");
		transactionMessage.append("<Transactions>");
		transactionMessage.append("<Username>" + user + "</Username>");
		transactionMessage.append("<Callback>" + user+ "-transaction</Callback>");
		transactionMessage.append("</Transactions>");
		transactionMessage.append("</BitmoneyExchange>");

	}
	
	void setBuyMessage() {
		user = preferences.getString("user", "");
		buyMessage = new StringBuffer();
		buyMessage.append("<BitmoneyExchange>");
		buyMessage.append("<Buy>");
		buyMessage.append("<Amount>" + result + "</Amount>");
		buyMessage.append("<UserId>" + userId + "</UserId>");
		buyMessage.append("<Callback>" + user+ "-buy</Callback>");
		buyMessage.append("</Buy>");
		buyMessage.append("</BitmoneyExchange>");

	}
	
	void setTransferMessage() {
		user = preferences.getString("user", "");
		transferMessage = new StringBuffer();
		transferMessage.append("<BitmoneyExchange>");
		transferMessage.append("<Transfer>");
		transferMessage.append("<Region>" + transferRegion + "</Region>");
		transferMessage.append("<Amount>" + result + "</Amount>");
		transferMessage.append("<UserId>" + userId + "</UserId>");
		transferMessage.append("<Callback>" + user+ "-transfer</Callback>");
		transferMessage.append("</Transfer>");
		transferMessage.append("</BitmoneyExchange>");

	}
	
	
	void setSellMessage() {
		user = preferences.getString("user", "");
		sellMessage = new StringBuffer();
		sellMessage.append("<BitmoneyExchange>");
		sellMessage.append("<Sell>");
		sellMessage.append("<Amount>" + result + "</Amount>");
		sellMessage.append("<UserId>" + userId + "</UserId>");
		sellMessage.append("<Callback>" + user+ "-sell</Callback>");
		sellMessage.append("</Sell>");
		sellMessage.append("</BitmoneyExchange>");

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		preferences = this.getSharedPreferences("org.zeroglitch.bitmoney",Context.MODE_PRIVATE);
		user = preferences.getString("user", "");

		// user="jamie";
		Log.i(TAG, "the user blah blah:" + user + "x");
		if ("".equals(user)) {
			setContentView(R.layout.preferences);

			Log.i(TAG, "jamie");
			userText = (EditText) findViewById(R.id.userEditText);
			userText.setText("wtjamiegmail.com");
			prefSendButton = (Button) findViewById(R.id.prefSendButton);
			prefSendButton.setOnClickListener(this);

			Spinner spinner = (Spinner) findViewById(R.id.regionSpinner);
			// Create an ArrayAdapter using the string array and a default
			// spinner layout
			ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(this, R.array.regions_array,
							android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinner.setAdapter(adapter);

		} else {
			preferences.edit().putString("NA", region).commit();

			myRegion = preferences.getString("region", "NA");
			
			if ("NA".equals(myRegion)) {
				myRegionValue = naCoinValue;
			} else if ("LTAM".equals(myRegion)) {
				myRegionValue = ltamCoinValue;
			} else if ("APAC".equals(myRegion)) {
				myRegionValue = apacCoinValue;
			} else if ("EMEA".equals(myRegion)) {
				myRegionValue = emeaCoinValue;
			}
			
			Log.i(TAG, "rate messages call");
			createConnection();
			connect();
			listener();
			setContentView(R.layout.main);
			setupView();
			setRatesMessage();
			currentMessage = ratesMessage;
			Log.i(TAG, "1");
			send("BITCOIN", ratesMessage.toString(), user + "-rates");
			Log.i(TAG, "2");

			setBalanceMessage();
			send("BITCOIN", balanceMessage.toString(), user + "-balance");

			setTransactionMessage();
			send("BITCOIN", transactionMessage.toString(), user + "-transaction");


			gridView = (GridView) findViewById(R.id.gridView);

			gridView.setNumColumns(3);

			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
			final String[] numbers = new String[] { "Date", "Credit", "Debit" };

			adapter.addAll(numbers);

			gridView.setAdapter(adapter);
			
			buyButton = (Button)findViewById(R.id.buyButton);
			buyButton.setOnClickListener(this);
			sellButton = (Button)findViewById(R.id.sellButton);  
			sellButton.setOnClickListener(this);
			transferButton = (Button)findViewById(R.id.transferButton);  
			transferButton.setOnClickListener(this);
		}

	}
	
	

	public void createConnection() {
		// Public Queue
		mqtt = new MQTT();
		if ("".equals(user)) user = userText.getText().toString().trim();   
		 
		Log.i(TAG,user + "request" + new java.util.Date().getTime());
		mqtt.setClientId(user + "request" + new java.util.Date().getTime());
		//sAddress = "tcp://www.zeroglitch.org:1883";
		sAddress = "tcp://192.168.1.101:1883";

		try {
			mqtt.setHost(sAddress);
			Log.d(TAG, "Address set: " + mqtt.getHost());
			Log.d(TAG, "client set: " + mqtt.getClientId());
		} catch (URISyntaxException urise) {
			Log.e(TAG, "URISyntaxException connecting to " + sAddress + " - " + urise);
		}

		
		Log.i(TAG, "the user: " + user);

	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			disconnect();
		} catch (Exception e) {};
		System.exit(0);
	}

	public void setupView() {

		east = (TextView) findViewById(R.id.eastText);
		west = (TextView) findViewById(R.id.westText);
		balance = (TextView) findViewById(R.id.balanceText);
	}

	public void updateUI() {
		balance.setText(balanceVal);
		west.setText(westVal);
		east.setText(eastVal);
	}

	public void onClick(View v) {
		Log.i(TAG, "click: " + v.toString());

		if (v == prefSendButton) {
			user = userText.getText().toString().trim();
			createConnection();

			connect();
			listener();
			setRegMessages();
			currentMessage = regMessage;

			send("BITCOIN", regMessage.toString(), user + "response");
			// listener();
		}
		
		if (v == buyButton) {
			Log.i(TAG, "buying");
			showBuyDialog("Buy");
		}
		
		if (v == sellButton) {
			showBuyDialog("Sell");
		}
		
		if (v== transferButton) {
			showTransferDialog("Sell");
			//setTransferMessage();
			//send("BITCOIN", transferMessage.toString(), user + "/rates");
		}

		if (v == sendButton) {
			connect();
			listener();
			Log.i(TAG, "SEND BUTTON");
			setRatesMessage();
			currentMessage = ratesMessage;
			send("BITCOIN", ratesMessage.toString(), user + "/rates");

		}
	}
	
	public void showBuyDialog(final String action) {
		Log.i(TAG, "showing it");

		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.prompts, null);
		
		TextView tv = (TextView)findViewById(R.id.textView1);
		tv.setText("Enter amount to " + action);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

		// set dialog message
		alertDialogBuilder
			.setCancelable(true)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    

					public void onClick(DialogInterface dialog,int id) {
					
					result = new String(userInput.getText()+"");
					Log.i(TAG, "the result: " + result);
					
					if (action.equals("Buy")) {
						setBuyMessage();
						send("BITCOIN", buyMessage.toString(), user + "-buy");
					} else {
						setSellMessage();
						send("BITCOIN", sellMessage.toString(), user +  "-sell");
						
					}
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		Log.i(TAG, "Before Show");
		alertDialog.show(); 
	}
	
	public void showTransferDialog(final String action) {
		Log.i(TAG, "showing it");

		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.transfer_prompt, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptsView);

		userInput = (EditText) promptsView.findViewById(R.id.transferDialogInput);
		userInput.setOnKeyListener(this);
		valueDisplay = (TextView)promptsView.findViewById(R.id.transferText);
	    regionSpinner = (Spinner) promptsView.findViewById(R.id.transferRegion);
		// Create an ArrayAdapter using the string array and a default
		// spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.regions_array,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		regionSpinner.setAdapter(adapter);
		
		regionSpinner.setOnItemSelectedListener(this);

		// set dialog message
		alertDialogBuilder.setCancelable(true).setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {	    

				public void onClick(DialogInterface dialog,int id) {
					
					result = new String(userInput.getText()+"");
					transferRegion = new String(regionSpinner.getSelectedItem().toString());
					Log.i(TAG, "the result: " + result);
					
					setTransferMessage();
					send("BITCOIN", transferMessage.toString(), user +  "-transfer");
				
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		Log.i(TAG, "Before Show");
		alertDialog.show(); 
	}


	// callback used for Future
	<T> Callback<T> onui(final Callback<T> original) {
		return new Callback<T>() {
			public void onSuccess(final T value) {
				runOnUiThread(new Runnable() {
					private String balanceVal;

					public void run() {
						original.onSuccess(value);

						if (value != null) {
							Log.i(TAG, "value haha: " + value.getClass() + " orig: "+ original.getClass());
							Log.i(TAG, "value string: " + value.toString());

							if (value instanceof org.fusesource.mqtt.client.Message) {
								Message m = (Message) value;

								String messagePayLoad = new String(m.getPayload());
								Log.i(TAG, "onui: " + messagePayLoad);

								if (messagePayLoad.equals("SUCCESS")) {
									setContentView(R.layout.main);
									setupView();
									Log.i(TAG, "added user");
									// Removed for testing
									preferences.edit().putString("user", user).commit();
									preferences.edit().putString("region", region).commit();
									preferences.edit().commit();

								} else if (messagePayLoad.indexOf("<") > -1) {
									try {
										Document doc = loadXMLFromString(messagePayLoad);

										NodeList n = doc.getElementsByTagName("Rates");

										Log.i(TAG,"length: rates "+ n.getLength());
										if (messagePayLoad.indexOf("<Rates>") > 0) {
											Log.i(TAG,"nodeList length: " + n.getLength());

											Node node = n.item(0);
											Element eElement = (Element) node;
											Log.i(TAG,"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + eElement .getElementsByTagName( "East") .item(0) .getTextContent());
											Log.i(TAG, eElement.getElementsByTagName( "West").item(0) .getTextContent()); 

											if (!"".equals(eElement .getElementsByTagName( "East").item(0) .getTextContent())) {
												east.setText(eElement .getElementsByTagName( "East").item(0) .getTextContent());
												eastVal = eElement .getElementsByTagName( "East").item(0) .getTextContent();
											}
											Log.i(TAG, "EASt: " + east.getText()); 
											if (!"".equals(eElement .getElementsByTagName( "West").item(0) .getTextContent())) {
												west.setText(eElement .getElementsByTagName( "West").item(0) .getTextContent());
												westVal = eElement .getElementsByTagName( "West").item(0) .getTextContent();
											}

										}

										n = doc.getElementsByTagName("Balance");
										if (messagePayLoad.indexOf("<Balance>") > 0) {
											Log.i(TAG, "nodeList length: " + n.getLength());

											if (n.getLength() > 0) {
												Node node = n.item(0);
												Element eElement = (Element) node;
												Log.i(TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + eElement .getElementsByTagName( "BalanceAmount") .item(0) .getTextContent());

												if (!"".equals(eElement .getElementsByTagName( "BalanceAmount") .item(0) .getTextContent())) {
													balanceVal = eElement .getElementsByTagName( "BalanceAmount") .item(0) .getTextContent();
													userId = eElement .getElementsByTagName( "UserId") .item(0) .getTextContent();

													balance.setText(balanceVal);
												}

											}
										}
										n = doc.getElementsByTagName("Sell");
										if (messagePayLoad.indexOf("<Sell>") > 0) {
											send("BITCOIN", balanceMessage.toString(), user + "-balance");
											send("BITCOIN", transactionMessage.toString(), user + "-transaction");
										}
										
										n = doc.getElementsByTagName("Buy");
										if (messagePayLoad.indexOf("<Buy>") > 0) {
											send("BITCOIN", balanceMessage.toString(), user + "-balance");
											Log.i(TAG, "sending transaction  message");
											send("BITCOIN", transactionMessage.toString(), user + "-transaction");


										}

										n = doc.getElementsByTagName("Transaction");
										if (messagePayLoad .indexOf("<Transactions>") > 0) {
											Log.i(TAG, "Transaction: " + messagePayLoad);
											Log.i(TAG, "XXX# # J##$#$J##J#J#J#J#J#J#J J#O#JO#$J O#$JO#$");
											// Node node = n.item(0);
											NodeList nl = doc .getElementsByTagName("Transactions");
											Log.i(TAG, "Transaction: " + messagePayLoad);
											adapter.clear();
											
											final String[] numbers = new String[] { "Date", "Credit", "Debit" };

											adapter.addAll(numbers);

											for (int i = 0; i < n.getLength(); i++) {
												Node node = n.item(i);
												Element eElement = (Element) node;
												adapter.add(eElement .getElementsByTagName( "TranDate") .item(0) .getTextContent());
												adapter.add(eElement .getElementsByTagName( "Credit") .item(0) .getTextContent());
												adapter.add(eElement .getElementsByTagName( "Debit") .item(0) .getTextContent());
											}
										}
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							}
						}
					}
				});
				// updateUI();

			}

			public void onFailure(final Throwable error) {
				runOnUiThread(new Runnable() {
					public void run() {
						original.onFailure(error);
					}
				});
			}
		};
	}

	private void connect() {

		if (((connection != null) && (!connection.isConnected()))
				|| (connection == null)) {
			connection = mqtt.futureConnection();
			progressDialog = ProgressDialog.show(this, "", "Connecting...",
					true);
			Log.i(TAG, "connecting()");
			connection.connect().then(onui(new Callback<Void>() {
				public void onSuccess(Void value) {
					Log.i(TAG, "connected");
					// connectButton.setEnabled(false);
					progressDialog.dismiss();
					toast("Connected");
				}

				public void onFailure(Throwable e) {
					toast("Problem connecting to host");
					Log.e(TAG, "Exception connecting to " + sAddress + " - "
							+ e);
					progressDialog.dismiss();
				}
			}));
		}

	}

	private void disconnect() {
		connectButton.setEnabled(true);
		try {
			if (connection != null && connection.isConnected()) {
				connection.disconnect().then(onui(new Callback<Void>() {
					public void onSuccess(Void value) {
						connectButton.setEnabled(true);
						toast("Disconnected");
					}

					public void onFailure(Throwable e) {
						toast("Problem disconnecting");
						Log.e(TAG, "Exception disconnecting from " + sAddress
								+ " - " + e);
					}
				}));
			} else {
				toast("Not Connected");
			}
		} catch (Exception e) {
			Log.e(TAG, "Exception " + e);
		}
	}

	private void send(final String topic, final String message, String callback) {
		Log.i(TAG, "send.in : " + callback);
		sMessage = currentMessage.toString();
		sDestination = "sDestination";
		if (connection != null) {

			Topic[] topics = { new Topic(callback, QoS.AT_LEAST_ONCE) };
			connection.subscribe(topics).then(onui(new Callback<byte[]>() {
				public void onSuccess(byte[] subscription) {

					Log.d(TAG, "Destination: " + topic);
					Log.d(TAG, "Message: " + message.toString());

					// publish message
					connection.publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE, false);

					toast("Message sent");
					// receive();

					// receive message
					connection.receive().then(onui(new Callback<Message>() {
						public void onSuccess(Message message) {
							String receivedMesageTopic = message.getTopic();
							byte[] payload = message.getPayload();
							String messagePayLoad = new String(payload);
							Log.i(TAG, "onSuccess message:  " + messagePayLoad);
							message.ack();
							connection.unsubscribe(new String[] { sDestination });
							Log.i(TAG, receivedMesageTopic + ":"+ messagePayLoad);

						}

						public void onFailure(Throwable e) {
							Log.e(TAG, "Exception receiving message: " + e);
						}
					}));

				}

				public void onFailure(Throwable e) {
					Log.e(TAG, "Exception sending message: " + e);
				}
			}));
		} else {
			toast("No connection has been made, please create the connection");
		}
		Log.i(TAG, "send.out");
	}

	private void receive() {
		if (connection != null) {
			// automatically connect if no longer connected
			if (!connection.isConnected()) {
				connect();
			}

			// receive message
			connection.receive().then(onui(new Callback<Message>() {
				public void onSuccess(Message message) {
					Log.i(TAG, "receive success.");
					String receivedMesageTopic = message.getTopic();
					Log.i(TAG, "receivedMesageTopic:" + receivedMesageTopic);
					byte[] payload = message.getPayload();
					String messagePayLoad = new String(payload);
					message.ack();
					// connection.unsubscribe(new String[] { sDestination });
					Log.i(TAG, receivedMesageTopic + ":" + messagePayLoad);
				}

				public void onFailure(Throwable e) {
					Log.e(TAG, "Exception receiving message: " + e);
				}
			}));

		} else {
			toast("No connection has been made, please create the connection");
		}
	}

	private void listener() {

		Topic[] topics = { new Topic(user + "response", QoS.AT_LEAST_ONCE) };
		// connection.sub
		connection.subscribe(topics).then(new Callback<byte[]>() {
			public void onSuccess(byte[] qoses) {
				Log.i(TAG, "CONNECTED TO LISTNER: callback succs: ");
			}

			public void onFailure(Throwable value) {
				Log.i(TAG, "failed to subscrib");
				value.printStackTrace();
				// System.exit(-2);
			}
		});

	}

	private void toast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	public static Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {		
		updateValue(position);
	}

	private void updateValue(int position) {
		
		
		Log.d(TAG, "valueDisplay " + valueDisplay);
		Log.d(TAG, "userInput " + userInput);
		
		if (userInput == null) return;
		if (valueDisplay == null) return;
		
		String userValue = userInput.getText() + "";
		String value = regionSpinner.getSelectedItem().toString();
		//double dValue = Double.parseDouble(valueDisplay.getText()+"");
		
		Log.d(TAG, " selected region: " + value);
		
		if ("".equals(userValue)) return;
		double input = Double.parseDouble(userValue);
		double userRegionValue = 1.0;
		double selectedRegionValue = 1.0;
		
		region = preferences.getString("region", "NA");
		
		if ("".equals(region)) {
			region = "NA";
		}
		Log.d(TAG, "USER REGSION: " + region);
		
		if ("NA".equalsIgnoreCase(region)) {
			userRegionValue = naCoinValue;
		} else if ("LTAM".equalsIgnoreCase(region)) {
			userRegionValue = ltamCoinValue;
		} else if ("APAC".equalsIgnoreCase(region)) {
			userRegionValue = apacCoinValue;
		} else if ("EMEA".equalsIgnoreCase(region)) {
			userRegionValue = emeaCoinValue;
		}
		
		if ("NA".equalsIgnoreCase(value)) {
			selectedRegionValue = naCoinValue;
		} else if ("LTAM".equalsIgnoreCase(value)) {
			selectedRegionValue = ltamCoinValue;
		} else if ("APAC".equalsIgnoreCase(value)) {
			selectedRegionValue = apacCoinValue;
		} else if ("EMEA".equalsIgnoreCase(value)) {
			selectedRegionValue = emeaCoinValue;
		}
		
		double ratio = 0.0;
		
		Log.d(TAG, "user: " + userRegionValue + " select: " + selectedRegionValue);
		
		ratio = userRegionValue/selectedRegionValue;
		
		valueDisplay.setText((ratio * input) + "");
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onKey(View view, int key, KeyEvent event) {
		updateValue(1);
		return false;
	}

}
