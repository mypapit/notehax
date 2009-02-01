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
 * Note.java
 * Basic Note class
 */

import java.io.*;
import javax.microedition.io.*;

public class Note implements Streamer
{
	public String title;
	public String content;
	public int recordID;
	byte[] data;




	public Note(String title, String content)
	{
		this.title = title;
		this.content = content;
	}

	public void write(byte[] data) {

		DataInputStream din;
		ByteArrayInputStream bin;

		try {
			bin = new ByteArrayInputStream(data);
			din = new DataInputStream(bin);

			title=din.readUTF();
			content=din.readUTF();
			//recordID = din.readInt();


			din.close();
			bin.close();
		} catch (IOException ioex) {
			System.out.println("Note : "+ioex.toString());
		} catch (Exception ex) {
			System.out.println("Note : "+ex.toString());
		}
		this.data=data;
	}

	public byte[] read()
	{
		DataOutputStream dos;
		ByteArrayOutputStream bos;
		byte[] data={0x00,0x00};
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);

			dos.writeUTF(title);
			dos.writeUTF(content);
			//dos.writeInt(recordID);

			data = bos.toByteArray();

			dos.close();
			bos.close();
			dos=null;
			bos =null;


		} catch (IOException ioex) {
			System.out.println("Note : "+ioex.toString());
		} catch (Exception ex) {
			System.out.println("Note : "+ex.toString());
		}


		return data;
	}

	public int length()
	{
		return data.length;
	}


}