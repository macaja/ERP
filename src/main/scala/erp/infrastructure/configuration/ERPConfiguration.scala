package erp.infrastructure.configuration

import com.typesafe.config.ConfigFactory
import erp.infrastructure.http.config.HttpConfig
import pureconfig.error.ConfigReaderFailures
import pureconfig.loadConfig

trait Config {
  def httpConfig: HttpConfig
}

object ERPConfiguration extends Config {

  val config = ConfigFactory.load()

  val validation: Either[ConfigReaderFailures, HttpConfig] = loadConfig[HttpConfig](config, "erp.accounting.http")

  val httpConfig = validation.fold(failure => throw new Exception(s"failed to load configuration $failure"),identity)

}
