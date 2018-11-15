/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2018-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.repository.puppet.internal.metadata;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.storage.Asset;

@Named
@Singleton
public class ModuleReleaseResultBuilder
{
  public ModuleReleasesResult parse(final Asset asset) {
    ModuleReleasesResult result = new ModuleReleasesResult();
    result.setSlug(parseSlug(asset.name(), asset.formatAttributes().get("version").toString()));
    result.setUri(parseUri(asset.name(), asset.formatAttributes().get("version").toString()));
    result.setVersion(asset.formatAttributes().get("version").toString());
    result.setFile_uri(parseFileUri(asset.name(), asset.formatAttributes().get("version").toString()));
    result.setFile_md5(getMd5Checksum(asset.attributes()));

    return result;
  }

  private String parseUri(final String name, final String version) {
    return String.format("/v3/releases/%s-%s", name, version);
  }

  private String parseSlug(final String name, final String version) {
    return String.format("%s-%s", name, version);
  }

  private String parseFileUri(final String name, final String version) {
    return String.format("/v3/files/%s-%s.tar.gz", name, version);
  }

  private String getMd5Checksum(final NestedAttributesMap attributesMap) {
    return attributesMap.get("checksum", Map.class).get("md5").toString();
  }
}