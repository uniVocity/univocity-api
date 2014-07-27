/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

/**
 * The <code>UnmatchedReference</code> configuration is obtained from a {@link ReferenceTransform} configuration
 * which is part of the configuration initialized by a call to {@link ReferenceMappingSetup#using(String...)}
 *
 * <p>It is used as an access point for further configuration options that define how to handle references that could not be matched.
 *
 * <p>For example, consider the following information in uniVocity's metadata:
 *
 * <hr><blockquote><pre>
 * Source entity (WGT)    Destination entity (weight)
 *     nbr | seq                    gid
 *     ----+----                  -------
 *     001 | 1                       1
 *     001 | 2                       2
 *     002 | 1                       3
 * </pre></blockquote><hr>
 *
 * <p>And the following mapping:
 * <p><hr><blockquote><pre>
 * EntityMapping weightDetails = mapping.map("WGT_DET", "weight_details");
 * weightDetails.reference().using("wgt_nbr", "wgt_seq").referTo("WGT", "weight").on("weight_ref");
 * </pre></blockquote><hr>
 *
 * <p>If a record in the source entity <code>WGT_DET</code> contains <code>wgt_nbr = 1, seq = 3</code> and this is used to refer to
 * <code>WGT</code>, no results will come from uniVocity's metadata.
 *
 * <p>This configuration class gives some options to handle such situations.
 *
 * @see ReferenceMappingSetup
 * @see UnmatchedReferenceHandling
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface UnmatchedReference {
	/**
	 * Returns the next step of this configuration: defining what action to take when a reference could not be matched.
	 * @return a configuration object to configure how to handle mismatched references.
	 */
	public UnmatchedReferenceHandling onMismatch();

}
