/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.gatein.pc.embed.actionurlparameter;

import junit.framework.Assert;
import org.gatein.pc.embed.AbstractTestCase;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

/** @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a> */
public class ActionURLParameterTestCase extends AbstractTestCase
{
   @ArquillianResource
   URL deploymentURL;

   @Deployment
   public static WebArchive deployment()
   {
      return deployment(PORTLET_APP_PROLOG +
         "<portlet>" +
         "<portlet-name>" + ActionURLParameterPortlet.class.getSimpleName() + "</portlet-name>" +
         "<portlet-class>" + ActionURLParameterPortlet.class.getName() + "</portlet-class>" +
         "<supports>\n" +
         "<mime-type>text/html</mime-type>\n" +
         "<portlet-mode>VIEW</portlet-mode>\n" +
         "<portlet-mode>EDIT</portlet-mode>\n" +
         "</supports>\n" +
         "<portlet-info>" +
         "<title>" + ActionURLParameterPortlet.class.getSimpleName() + "</title>" +
         "</portlet-info>" +
         "</portlet>" +
         PORTLET_APP_EPILOG);
   }

   @Drone
   WebDriver driver;

   @Test
   @RunAsClient
   @InSequence(0)
   public void init()
   {
      Assert.assertNull(ActionURLParameterPortlet.actionFoo);
      Assert.assertNull(ActionURLParameterPortlet.actionPortletMode);
      Assert.assertNull(ActionURLParameterPortlet.actionWindowState);
      Assert.assertNull(ActionURLParameterPortlet.renderFoo);
      Assert.assertNull(ActionURLParameterPortlet.renderPortletMode);
      Assert.assertNull(ActionURLParameterPortlet.renderWindowState);
   }

   @Test
   @RunAsClient
   @InSequence(1)
   public void display() throws Exception
   {
      URL url = renderURL(deploymentURL, ActionURLParameterPortlet.class);
      driver.get(url.toString());
   }

   @Test
   @RunAsClient
   @InSequence(2)
   public void testDisplay()
   {
      Assert.assertNull(ActionURLParameterPortlet.actionFoo);
      Assert.assertNull(ActionURLParameterPortlet.actionPortletMode);
      Assert.assertNull(ActionURLParameterPortlet.actionWindowState);
      Assert.assertNull(ActionURLParameterPortlet.renderFoo);
      Assert.assertEquals(PortletMode.VIEW, ActionURLParameterPortlet.renderPortletMode);
      Assert.assertEquals(WindowState.NORMAL, ActionURLParameterPortlet.renderWindowState);
   }

   @Test
   @RunAsClient
   @InSequence(3)
   public void interaction() throws Exception
   {
      WebElement link = driver.findElement(By.id("url"));
      link.click();
   }

   @Test
   @RunAsClient
   @InSequence(4)
   public void testInteraction()
   {
      Assert.assertNotNull(ActionURLParameterPortlet.actionFoo);
      Assert.assertEquals(Collections.singletonList("foo_value"), Arrays.asList(ActionURLParameterPortlet.actionFoo));
      Assert.assertEquals(PortletMode.EDIT, ActionURLParameterPortlet.actionPortletMode);
      Assert.assertEquals(WindowState.MAXIMIZED, ActionURLParameterPortlet.actionWindowState);
      Assert.assertNull(ActionURLParameterPortlet.renderFoo);
      Assert.assertEquals(PortletMode.EDIT, ActionURLParameterPortlet.renderPortletMode);
      Assert.assertEquals(WindowState.MAXIMIZED, ActionURLParameterPortlet.renderWindowState);
   }
}
