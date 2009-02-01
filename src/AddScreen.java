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
 * AddScreen.java
 * Screen for adding new notes
 */


import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class AddScreen extends Form
{
	TextField itemTitle;
	Command cmdOK;
	public AddScreen(String title)
	{
		super(title);

		itemTitle = new TextField("Item Title","",50,TextField.ANY);


		cmdOK = new Command("OK",Command.OK,1);

		addCommand(new Command("Cancel",Command.CANCEL,100));
		addCommand(cmdOK);
		append(itemTitle);

	}

	public String getString()
	{
		return itemTitle.getString();
	}

	public void setString(String content)
	{

		itemTitle.setString(content);
	}




}