import { CancelToken } from "axios";
import { CANCEL } from "redux-saga";

import baseHttp from "./baseHttp";

//This Http class wraps a provider (in this an axios instance)
//and sets up cancellation in the context of sagas (where all network requests should occur in this application)

class Http {
  constructor(httpProvider) {
    this._provider = httpProvider;
  }

  get(url = "", params = {}) {
    const source = CancelToken.source();
    let request = this._provider.get(url, {
      params,
      cancelToken: source.token
    });
    request[CANCEL] = () => source.cancel();
    return request;
  }

  post(url = "", data = {}) {
    const source = CancelToken.source();
    let request = this._provider.post(url, {
      data,
      cancelToken: source.token
    });
    request[CANCEL] = () => source.cancel();
    return request;
  }
}

const http = new Http(baseHttp);

export default http;
