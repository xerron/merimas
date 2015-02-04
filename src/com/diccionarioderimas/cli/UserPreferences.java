/***************************************************************************
 *   Copyright (C) 2009 by Eduardo Rodriguez Lorenzo                       *
 *   edu.tabula@gmail.com                                                  *
 *   http://www.cronopista.com                                             *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/
package com.diccionarioderimas.cli;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;


public class UserPreferences {

	private boolean yEquivalence = true;
	private boolean zEquivalence = false;
	private boolean ceEquivalence = false;
	private boolean liEquivalence = false;
	private boolean niEquivalence = false;

	private String basePath;

	public UserPreferences(String basePath) {
		this.basePath = basePath;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(basePath + "merimas.properties"));
			String yEquival = prop.getProperty("yEquivalence");
			String zEquival = prop.getProperty("zEquivalence");
			String ceEquival = prop.getProperty("ceEquivalence");
			String liEquival = prop.getProperty("liEquivalence");
			String niEquival = prop.getProperty("niEquivalence");

			if (yEquival != null)
				yEquivalence = Boolean.parseBoolean(yEquival);
			if (zEquival != null)
				zEquivalence = Boolean.parseBoolean(zEquival);
			if (ceEquival != null)
				ceEquivalence = Boolean.parseBoolean(ceEquival);
			if (liEquival != null)
				liEquivalence = Boolean.parseBoolean(liEquival);
			if (ceEquival != null)
				niEquivalence = Boolean.parseBoolean(niEquival);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void save() {
		Properties prop = new Properties();
		try {
			prop.setProperty("yEquivalence", yEquivalence + "");
			prop.setProperty("zEquivalence", zEquivalence + "");
			prop.setProperty("ceEquivalence", ceEquivalence + "");
			prop.setProperty("liEquivalence", liEquivalence + "");
			prop.setProperty("niEquivalence", niEquivalence + "");
			prop.store(
					new FileOutputStream(basePath + "merimas.properties"),
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isYEquivalence() {
		return yEquivalence;
	}

	public void setYEquivalence(boolean equivalence) {
		yEquivalence = equivalence;
	}

	public boolean isZEquivalence() {
		return zEquivalence;
	}

	public void setZEquivalence(boolean equivalence) {
		zEquivalence = equivalence;
	}

	public boolean isCeEquivalence() {
		return ceEquivalence;
	}

	public void setCeEquivalence(boolean ceEquivalence) {
		this.ceEquivalence = ceEquivalence;
	}

	public String getBasePath() {
		return basePath;
	}

	public boolean isLiEquivalence() {
		return liEquivalence;
	}

	public void setLiEquivalence(boolean liEquivalence) {
		this.liEquivalence = liEquivalence;
	}

	public boolean isNiEquivalence() {
		return niEquivalence;
	}

	public void setNiEquivalence(boolean niEquivalence) {
		this.niEquivalence = niEquivalence;
	}


}
