package com.gaocegege.scrala.core.middleware.filter.impl

import com.gaocegege.scrala.core.middleware.filter.Filter
import com.google.common.hash.{BloomFilter, Funnels}
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

class UrlBloomFilter(expectedInsertions: Int = 1000000, falsePositiveProbability: Double = 0.01) extends Filter {
  private val bf = BloomFilter.create[String](
    Funnels.unencodedCharsFunnel, expectedInsertions,0.01)

  val logger = Logger(LoggerFactory getLogger "urldupfilter")

  def filter(url: String): Boolean = {
    if (bf.mightContain(url)) {
      logger debug ("Duplicate url: " + url)
      false
    } else {
      bf.put(url)
      true
    }
  }
}
