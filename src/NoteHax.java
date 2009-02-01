/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as published by
 *  the Free Software Foundation
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * NoteHax 1.0 <info@mypapit.net>
 * Copyright 2009 Mohammad Hafiz bin Ismail. All rights reserved.
 *
 * NoteHax.java
 * A simple note storing application
 */


import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import javax.microedition.rms.*;
import javax.wireless.messaging.*;
import java.util.*;
import java.io.*;
public class NoteHax extends MIDlet implements ItemCommandListener, CommandListener,RecordListener {


List mainForm,sendList;
Display display;

Command cmdAdd,cmdDelete,cmdAbout,cmdSend;
AddScreen addScreen;
ContentScreen contentScreen,editScreen;
Note nNote,tempNote;
Form viewForm;
SendResult sendResult;
Hashtable noteList;

public NoteHax() {

	//initialize form
	mainForm = new List("NoteHax",List.IMPLICIT);

	noteList =new Hashtable(20);
	//initialize command


	cmdAdd = new Command("Add",Command.SCREEN,2);
	cmdDelete = new Command("Delete",Command.SCREEN,3);
	cmdSend = new Command("Send",Command.SCREEN,65);
	cmdAbout = new Command("About",Command.HELP,99);
	mainForm.addCommand(new Command("Exit",Command.EXIT,100));
	mainForm.addCommand(cmdAdd);
	mainForm.addCommand(cmdDelete);
	mainForm.addCommand(cmdAbout);
//	mainForm.addCommand(cmdSend);



	addScreen = new AddScreen("add Item");
	contentScreen =new ContentScreen(addScreen.getString()+"'s content","",1000,TextField.ANY);
	//initialize display



	mainForm.setCommandListener(this);

}

public void startApp()
{
	//display the form and splashscreen
	if (display == null) {
		display = display.getDisplay(this);
		display.setCurrent(new SplashCanvas(this));
		loadNote();
		sendResult = new SendResult(new Note("title","content"));
	} else {
		display.setCurrent(mainForm);

	}





}



public void pauseApp() {

}


public void destroyApp(boolean flag) {
	notifyDestroyed();
}


// handle and listen for Command button action
public void commandAction(Command cmd, Displayable disp) {
		if (cmd.getCommandType() ==Command.EXIT) {
			destroyApp(false);
		} else if (cmd == cmdAdd) {
			addScreen.setString("");

			addScreen.setCommandListener(this);
			display.setCurrent(addScreen);
		} else if (cmd == addScreen.cmdOK) {
			if (addScreen.getString().length() <3) {
				Alert alert = new Alert("Information","Please fill in the title field",null,AlertType.INFO);
				alert.setTimeout(1750);
				display.setCurrent(alert,addScreen);
				return;
			}

			if (noteList.containsKey(addScreen.getString())) {
				Alert alert = new Alert("Information","Title already exists! Please choose another title",null,AlertType.INFO);
				alert.setTimeout(1200);
				addScreen.setString("");
				display.setCurrent(alert,addScreen);
				return;
			}
			contentScreen =new ContentScreen(addScreen.getString()+"'s content","",1000,TextField.ANY);
			contentScreen.setCommandListener(this);
			display.setCurrent(contentScreen);
		} else if (cmd == contentScreen.cmdSave) {
			nNote = new Note(addScreen.getString(),contentScreen.getString());
			this.saveNote();
			display.setCurrent(mainForm);
		} else if (cmd == cmdDelete) {
			if (mainForm.size() > 0) {
					display.setCurrent(new ConfirmDialog("Delete Confirmation",mainForm,this));
			}


		} else if (cmd == mainForm.SELECT_COMMAND && disp == mainForm) {
			String key=mainForm.getString(mainForm.getSelectedIndex());
			viewNote((Note) noteList.get(key));

		} else if (cmd.getCommandType() == Command.BACK || cmd.getCommandType() == Command.CANCEL) {
			display.setCurrent(mainForm);
		} else if (cmd == cmdAbout) {
			AboutForm aboutForm = new AboutForm("About","NoteHax " + getAppProperty("MIDlet-Version"),"/i.png");
			aboutForm.setCopyright("Mohammad Hafiz","2009");
			aboutForm.setHyperlink("http://notehax.googlecode.com",this);
			aboutForm.setCommandListener(this);
			aboutForm.append("For storing random notes and rants\n\nThis program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License version 2.0");
			display.setCurrent(aboutForm);
		} else if (cmd.getLabel().equals("Edit")) {
			editScreen =new ContentScreen("Editing "+ viewForm.getTitle(),"",1000,TextField.ANY);
			editScreen.setCommandListener(this);
			Note note = (Note) noteList.get(viewForm.getTitle());
			editScreen.setString(note.content);
			editScreen.record = note.recordID;
			editScreen.setCommandListener(this);
			display.setCurrent(editScreen);
		} else if (cmd == cmdSend) {
				sendResult = new SendResult(tempNote);
				sendResult.tfPhoneNo.setItemCommandListener(this);
				sendResult.setCommandListener(this);
				display.setCurrent(sendResult);

		} else if (cmd == editScreen.cmdSave) {
				nNote = new Note(viewForm.getTitle(),editScreen.getString());
				nNote.recordID = editScreen.record;
				editNote(nNote);
				display.setCurrent(mainForm);
		}

}

public void commandAction(Command cmd, Item item)
{
	 if (cmd == sendResult.cmdSMS) {

					if (sendResult.getPhoneNo().length() < 5) {
						Alert alert = new Alert("Error","Please enter destination phone number",null,AlertType.WARNING);
						display.setCurrent(alert,sendResult);
						return;
					}
					new SendSMS(this).start();

		}

}

public void saveNote()
{
	RecordStore record;

	try {
		record = RecordStore.openRecordStore("notes",true);
		record.addRecordListener(this);
		record.addRecord(nNote.read(),0,nNote.read().length);
		record.closeRecordStore();
	} catch (Exception ex)
	{
		System.out.println("saveNote "+ex.toString());
	}

}

public void editNote(Note nNote)
{
	RecordStore record;

	try {
		record = RecordStore.openRecordStore("notes",true);
		record.addRecordListener(this);
		record.setRecord(nNote.recordID,nNote.read(),0,nNote.read().length);
		record.closeRecordStore();

		noteList.remove(nNote.title);
		noteList.put(nNote.title,nNote);
	} catch (Exception ex)
	{
		System.out.println("editNote "+ex.toString());
	}

}

public void recordAdded(RecordStore recordStore, int id)
{
	mainForm.append(nNote.title,null);
	nNote.recordID = id;
	noteList.put(nNote.title,nNote);

}

public void recordChanged(RecordStore recordStore, int id)
{

}

public void recordDeleted(RecordStore recordStore, int id)
{
	showAlert("Confirmed","Note has been deleted", AlertType.CONFIRMATION);
	mainForm.delete(mainForm.getSelectedIndex());


}

public void loadNote()
{
		RecordStore record;
		RecordEnumeration renum;
		Note temp;
		int j;
		try {
			record = RecordStore.openRecordStore("notes",true);

			renum=record.enumerateRecords(null,null,false);

			int i =0;
			int size = renum.numRecords();
			int[] id = new int[size];


			while(renum.hasNextElement()) {
				id[i] = renum.nextRecordId();
				i++;
			}


			for (j=0;j<i;j++) {

				temp = new Note(""+j,"");
				temp.write(record.getRecord(id[j]));
				temp.recordID = id[j];
				noteList.put(temp.title,temp);
				mainForm.append(temp.title,null);

			}
			renum.destroy();
			record.closeRecordStore();
		} catch (Exception ex)
		{
			System.out.println("loadNote "+ex.toString());
		}

}

public void deleteNote(Note note)
{
	RecordStore record;

		try {
			record = RecordStore.openRecordStore("notes",true);
			record.addRecordListener(this);

			record.deleteRecord(note.recordID);
			record.closeRecordStore();

		} catch (Exception ex)
		{
			this.showAlert("Error","Couldn't delete note: "+ ex.toString(), AlertType.WARNING);
			System.out.println("Delete Note "+ex.toString());
		}



}

public void loadList()
{
	Enumeration enumerate = noteList.keys();
	//String notex;
		while (enumerate.hasMoreElements())
		{
			//notex =
			mainForm.append((String) enumerate.nextElement(),null);
		}

}

public void viewNote(Note note)
{
				tempNote = note;
				viewForm = new Form(note.title);
				viewForm.addCommand(new Command("Back",Command.BACK,100));
				viewForm.addCommand(new Command("Edit",Command.SCREEN,50));
				viewForm.addCommand(cmdSend);
				viewForm.setCommandListener(this);
				viewForm.append(note.content);
				display.setCurrent(viewForm);
}



public void showList()
{
	display.setCurrent(mainForm);
}

public void showAlert(String title,String text, AlertType alertype)
{
	Alert a = new Alert(title,text,null, alertype);
	a.setTimeout(Alert.FOREVER);
	display.setCurrent(a,mainForm);

}

public void showAlert(String text)
{
	showAlert("Error",text, AlertType.WARNING);

}


}


//
//
//sendSMS class
//
//

class SendSMS implements Runnable {
private NoteHax midlet;
private Display display;

private Form formRunning;

public SendSMS(NoteHax midlet)
{
	this.midlet = midlet;
	display = midlet.display;
	formRunning = new Form("Sending Result");

	formRunning.append(new Gauge("Processing...",false,Gauge.INDEFINITE,Gauge.CONTINUOUS_RUNNING));
	display.setCurrent(formRunning);

}

public void start() {
      new Thread(this).start();
}

public void run() {

StringBuffer sb = new StringBuffer("");
MessageConnection conn=null;
boolean onError=false;
	try {
		String addr = "sms://" + midlet.sendResult.getPhoneNo();
		conn = (MessageConnection) Connector.open(addr);
		TextMessage msg =
		(TextMessage)conn.newMessage(MessageConnection.TEXT_MESSAGE);
		sb.append("["+midlet.tempNote.title +"]\n\n" );
		sb.append(midlet.tempNote.content+ "\n");
		msg.setPayloadText(sb.toString());
		sb = null;
		conn.send(msg);
		display.setCurrent(midlet.sendResult);
		conn.close();
		} catch (IllegalArgumentException iae) {
			midlet.showAlert("Please fill in the form");
			onError = !onError;

		} catch (java.lang.SecurityException sex){
			midlet.showAlert("You need to give permission for message sending");
			onError = !onError;
		} catch (Exception e) {
			midlet.showAlert("Send Result failed :" + e.toString());
			onError = !onError;
		} finally {
				try {
					if (conn != null) {
							conn.close();
							conn = null;
					}
				} catch (Exception ignored) {

				}
		}
		if (!onError) {
			midlet.showAlert("Confirmed","Note has been sent to: " + midlet.sendResult.getPhoneNo(), AlertType.CONFIRMATION);
		}
	}


}
