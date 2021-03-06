/******************************************************************************
 * JBoss, a division of Red Hat                                               *
 * Copyright 2006, Red Hat Middleware, LLC, and individual                    *
 * contributors as indicated by the @authors tag. See the                     *
 * copyright.txt in the distribution for a full listing of                    *
 * individual contributors.                                                   *
 *                                                                            *
 * This is free software; you can redistribute it and/or modify it            *
 * under the terms of the GNU Lesser General Public License as                *
 * published by the Free Software Foundation; either version 2.1 of           *
 * the License, or (at your option) any later version.                        *
 *                                                                            *
 * This software is distributed in the hope that it will be useful,           *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU           *
 * Lesser General Public License for more details.                            *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public           *
 * License along with this software; if not, write to the Free                *
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA         *
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.                   *
 ******************************************************************************/
package javax.portlet;

import java.util.Map;

/**
 * The <CODE>PortletURL</CODE> interface represents a URL that reference the portlet itself.
 * A PortletURL is created through the <CODE>RenderResponse</CODE>. Parameters, a portlet mode, a window state and a
 * security level can be added to <CODE>PortletURL</CODE> objects. The PortletURL must be converted to a String in order
 * to embed it into the markup generated by the portlet.
 * There are two types of PortletURLs: <ul> <li>Action URLs, they are created with
 * <CODE>RenderResponse.createActionURL</CODE>, and trigger an action request followed by a render request. <li>Render
 * URLs, they are created with <CODE>RenderResponse.createRenderURL</CODE>, and trigger a render request. </ul>
 * The string reprensentation of a PortletURL does not need to be a valid URL at the time the portlet is generating its
 * content. It may contain special tokens that will be converted to a valid URL, by the portal, before the content is
 * returned to the client.
 *
 * @author <a href="mailto:julien@jboss.org">Julien Viet</a>
 * @version $Revision: 5441 $
 */
public interface PortletURL
{
   /**
    * Sets the given String parameter to this URL.
    * This method replaces all parameters with the given key.
    * The <code>PortletURL</code> implementation 'x-www-form-urlencoded' encodes all  parameter names and values.
    * Developers should not encode them.
    * A portlet container may prefix the attribute names internally in order to preserve a unique namespace for the
    * portlet.
    *
    * @param name  the parameter name
    * @param value the parameter value
    * @throws java.lang.IllegalArgumentException
    *          if name or value are <code>null</code>.
    */
   void setParameter(String name, String value) throws IllegalArgumentException;

   /**
    * Sets the given String array parameter to this URL.
    * This method replaces all parameters with the given key.
    * The <code>PortletURL</code> implementation 'x-www-form-urlencoded' encodes all  parameter names and values.
    * Developers should not encode them.
    * A portlet container may prefix the attribute names internally in order to preserve a unique namespace for the
    * portlet.
    *
    * @param name   the parameter name
    * @param values the parameter values
    * @throws java.lang.IllegalArgumentException
    *          if name or values are <code>null</code>.
    */
   void setParameter(String name, String[] values) throws IllegalArgumentException;

   /**
    * Sets a parameter map for this URL.
    * All previously set parameters are cleared.
    * The <code>PortletURL</code> implementation 'x-www-form-urlencoded' encodes all  parameter names and values.
    * Developers should not encode them.
    * A portlet container may prefix the attribute names internally, in order to preserve a unique namespace for the
    * portlet.
    *
    * @param parameters Map containing parameter names for the render phase as keys and parameter values as map values.
    *                   The keys in the parameter map must be of type String. The values in the parameter map must be of
    *                   type String array (<code>String[]</code>).
    * @exception java.lang.IllegalArgumentException if parameters is <code>null</code>, if any of the key/values in the
    * Map are <code>null</code>, if any of the keys is not a String, or if any of the values is not a String array.
    */
   void setParameters(Map parameters) throws IllegalArgumentException;

   /**
    * Indicates the portlet mode the portlet must be in, if this portlet URL triggers a request.
    * A URL can not have more than one portlet mode attached to it. If more than one portlet mode is set only the last
    * one set is attached to the URL.
    *
    * @param portletMode the portlet mode
    * @throws PortletModeException if the portlet cannot switch to this mode, because the portal does not support this
    *                              mode, the portlet has not declared in its deployment descriptor that it supports this
    *                              mode for the current markup, or the current user is not allowed to switch to this
    *                              mode. The <code>PortletRequest.isPortletModeAllowed()</code> method can be used to
    *                              check if the portlet can set a given portlet mode.
    * @see PortletRequest#isPortletModeAllowed
    */
   void setPortletMode(PortletMode portletMode) throws PortletModeException;

   /**
    * Indicated the security setting for this URL.
    * Secure set to <code>true</code> indicates that the portlet requests a secure connection between the client and the
    * portlet window for this URL. Secure set to <code>false</code> indicates that the portlet does not need a secure
    * connection for this URL. If the security is not set for a URL, it will stay the same as the current request.
    *
    * @param secure true, if portlet requests to have a secure connection between its portlet window and the client;
    *               false, if the portlet does not require a secure connection.
    * @throws PortletSecurityException if the run-time environment does not support the indicated setting
    */
   void setSecure(boolean secure) throws PortletSecurityException;

   /**
    * Indicates the window state the portlet should be in, if this portlet URL triggers a request.
    * A URL can not have more than one window state attached to it. If more than one window state is set only the last
    * one set is attached to the URL.
    *
    * @param windowState the portlet window state
    * @throws WindowStateException if the portlet cannot switch to this state, because the portal does not support this
    *                              state, the portlet has not declared in its deployment descriptor that it supports
    *                              this state, or the current user is not allowed to switch to this state. The
    *                              <code>PortletRequest.isWindowStateAllowed()</code> method can be used to check if the
    *                              portlet can set a given window state.
    * @see PortletRequest#isWindowStateAllowed
    */
   void setWindowState(WindowState windowState) throws WindowStateException;

   /**
    * Returns the portlet URL string representation to be embedded in the markup.<br> Note that the returned String may
    * not be a valid URL, as it may be rewritten by the portal/portlet-container before returning the markup to the
    * client.
    *
    * @return the encoded URL as a string
    */
   String toString();
}
