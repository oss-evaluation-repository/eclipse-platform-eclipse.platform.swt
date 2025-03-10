/*******************************************************************************
 * Copyright (c) 2024 Yatta Solutions
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Yatta Solutions - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.junit.*;

public abstract class Win32AutoscaleTestBase {
	private Display display;
	protected Shell shell;
	private boolean autoScaleOnRuntime;

	@BeforeClass
	public static void assumeIsFittingPlatform() {
		PlatformSpecificExecution.assumeIsFittingPlatform();
	}

	@Before
	public void setUpTest() {
		autoScaleOnRuntime = DPITestUtil.isAutoScaleOnRuntimeActive();
		display = Display.getDefault();
		autoScaleOnRuntime(true);
		shell = new Shell(display);
	}

	@After
	public void tearDownTest() {
		if (shell != null) {
			shell.dispose();
		}
		autoScaleOnRuntime(autoScaleOnRuntime);
	}

	protected void autoScaleOnRuntime (boolean autoScaleOnRuntime) {
		DPITestUtil.setAutoScaleOnRunTime(autoScaleOnRuntime);
		// dispose a existing font registry for the default display
		SWTFontProvider.disposeFontRegistry(display);
	}

	protected void changeDPIZoom (int nativeZoom) {
		Event event = new Event();
		event.type = SWT.ZoomChanged;
		event.widget = shell;
		event.detail = nativeZoom;
		event.doit = true;
		shell.notifyListeners(SWT.ZoomChanged, event);
	}
}