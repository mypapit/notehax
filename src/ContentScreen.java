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
 * ContentScreen.java
 * Screen for adding new notes content
 */


import javax.microedition.lcdui.*;

public class ContentScreen extends TextBox
{

	Command cmdSave;
	int record;

	public ContentScreen(String title, String content, int size, int constraints)
	{
		super(title,content,size,constraints);

		cmdSave = new Command("Save",Command.SCREEN,1);
		addCommand(new Command("Cancel",Command.CANCEL,100));
		addCommand(cmdSave);


	}

}