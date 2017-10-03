/*
 *   Copyright (C) 2015 Karumi.
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package cl.anpetrus.prueba4;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

  private static final String TIMESTAMP_KEY = "ts";
  private static final String HASH_KEY = "hash";
  private static final String APIKEY_KEY = "apikey";

  private final String publicKey = "8b312824cae040a1358d8fc0d366e0e8";
  private final String privateKey = "c56712a644f49ae21dc8950725254f4f4ebd2bc3";
  private final TimeProvider timeProvider;
  private final AuthHashGenerator authHashGenerator = new AuthHashGenerator();

  public AuthInterceptor() {
    this.timeProvider =  new TimeProvider();
  }

  @Override public Response intercept(Chain chain) throws IOException {
    String timestamp = String.valueOf(timeProvider.currentTimeMillis());
    String hash = null;
    try {
      hash = authHashGenerator.generateHash(timestamp,publicKey,privateKey);
    } catch (MarvelApiException e) {
      e.printStackTrace();
    }
    Request request = chain.request();
    HttpUrl url = request.url()
        .newBuilder()
        .addQueryParameter(TIMESTAMP_KEY, timestamp)
        .addQueryParameter(APIKEY_KEY, publicKey)
        .addQueryParameter(HASH_KEY, hash)
        .build();
    request = request.newBuilder().url(url).build();
    return chain.proceed(request);
  }
}
