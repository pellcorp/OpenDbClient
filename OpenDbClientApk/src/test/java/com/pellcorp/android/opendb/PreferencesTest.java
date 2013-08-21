package com.pellcorp.android.opendb;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import junit.framework.TestCase;

public class PreferencesTest extends TestCase {
	public void testPreferences() {
		PreferenceProvider prefs = mock(PreferenceProvider.class);
		when(prefs.getString(R.string.pref_password)).thenReturn("password");
		when(prefs.getString(R.string.pref_username)).thenReturn("username");
		when(prefs.getString(R.string.pref_host_url)).thenReturn("http://localhost/opendb");
		
		Preferences preferences = new Preferences(prefs);
		
		assertEquals("username", preferences.getUsername());
		assertEquals("password", preferences.getPassword());
		assertEquals("http://localhost/opendb", preferences.getHost());
	}
}
