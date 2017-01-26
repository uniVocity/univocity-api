/*
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.univocity.api.engine;

import java.io.*;

public interface DataStreamingProcess extends Closeable{

	void start();

	void resynchronize();

	void close();

}
