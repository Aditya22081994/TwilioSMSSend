/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twilioSMS.TwilioProject;

import java.io.IOException;
import java.util.*;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SendTwilioSms extends HttpServlet {

	@Override
	  public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException,
	      ServletException {
	    final String twilioAccountSid = System.getenv("TWILIO_ACCOUNT_SID");
	    final String twilioAuthToken = System.getenv("TWILIO_AUTH_TOKEN");
	    final String twilioNumber = System.getenv("TWILIO_NUMBER");
	    final String toNumber = "+918790752286";
	    		//(String) req.getParameter("to");
	    
	    Generation onetime = new Generation();
	    int otp = onetime.generateOTP();
	   
	    TwilioRestClient client = new TwilioRestClient(twilioAccountSid, twilioAuthToken);
	    Account account = client.getAccount();
	    MessageFactory messageFactory = account.getMessageFactory();
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("To", toNumber));
	    params.add(new BasicNameValuePair("From", twilioNumber));
	    params.add(new BasicNameValuePair("Body", "Your One Time Password is"+otp));
	    try {
	      Message sms = messageFactory.create(params);
	      resp.getWriter().print(sms.getBody());
	    } catch (TwilioRestException e) {
	      throw new ServletException("Twilio error", e);
	    
	  }
	}
}
