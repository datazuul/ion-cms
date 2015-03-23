package org.nextime.ion.backoffice.indexation;

import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.nextime.ion.framework.config.Config;
import org.nextime.ion.framework.helper.Indexer;
import org.nextime.ion.framework.mapping.Mapping;

public class IndexationServlet extends HttpServlet implements Runnable {

	private int minuteDelay = 60;
	private Thread thread;

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		try {
			minuteDelay =
				Integer.parseInt(getServletConfig().getInitParameter("delay"));
		} catch (Exception e) {
		}
		thread = new Thread(this,"Indexation");
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (true) {				
			String[] indexs = Config.getInstance().getIndexNames();
			for( int i=0; i<indexs.length; i++ ) {
				File rep = new File( Config.getInstance().getIndexRoot(), indexs[i] );
				if( !rep.exists() ) {
					rep.mkdir();
				}
			}
			try {
				System.out.println("["+Thread.currentThread().getName()+"] ReIndexation");		
				Mapping.begin();
					Indexer.reIndex();
				Mapping.rollback();	
				thread.sleep(minuteDelay * 60 * 1000);			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the minuteDelay.
	 * @return int
	 */
	public int getMinuteDelay() {
		return minuteDelay;
	}

	/**
	 * Sets the minuteDelay.
	 * @param minuteDelay The minuteDelay to set
	 */
	public void setMinuteDelay(int minuteDelay) {
		this.minuteDelay = minuteDelay;
	}

}
