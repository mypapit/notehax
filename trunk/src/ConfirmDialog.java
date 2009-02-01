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
 * ConfirmDialog.java
 * A generic Yes/No Dialog
 */


import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class ConfirmDialog extends Form implements CommandListener
{

	StringItem itemTitle;
	Command cmdYes,cmdNo;
	NoteHax midlet;
	Screen prev;
	public ConfirmDialog(String title, Screen prev,NoteHax midlet)
	{
		super(title);

		this.midlet = midlet;
		this.prev = prev;




		cmdYes= new Command("Yes",Command.OK,1);
		cmdNo= new Command("No",Command.CANCEL,1);

		itemTitle = new StringItem(null,"Do you really want to delete this item?\n\n(Note: You can't undo this operation)");


		addCommand(cmdYes);
		addCommand(cmdNo);
		append(itemTitle);

		setCommandListener(this);

	}

	public void setString(String content)
	{

		itemTitle.setText(content);
	}

	public void commandAction(Command cmd, Displayable disp) {
		if (cmd == cmdNo)
		{
			midlet.display.setCurrent(prev);
		} else if (cmd == cmdYes) {
			String key=midlet.mainForm.getString(midlet.mainForm.getSelectedIndex());

			midlet.deleteNote((Note) midlet.noteList.get(key));
			//midlet.display.setCurrent(prev);

		}


	}




}