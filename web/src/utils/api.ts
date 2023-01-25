/* eslint-disable import/prefer-default-export */
/* eslint-disable @typescript-eslint/no-explicit-any */
import {env} from "$env/dynamic/public";
import axios, {type AxiosRequestConfig, type Method} from "axios";

// import { getCookie } from "src/utils/cookie";

class RequestConfig {
    public baseURL: string;

    public headers?: any;

    public method?: Method;

    public url?: string;

    public data?: any;

    constructor(baseURL: string) {
        this.baseURL = baseURL;
    }

    public setAuthorization(token?: any) {
        if (token) {
            this.headers = {
                ...this.headers,
                Authorization: `Bearer ${token}`,
            };
        }
    }

    public setUuid(uuid: string) {
        this.headers = {
            ...this.headers,
            "Device-uuid": uuid,
        };
    }

    public get(path: string, config?: AxiosRequestConfig) {
        this.method = "GET";
        this.url = this.baseURL + path;

        return { ...config, ...this };
    }

    public delete(path: string, config?: AxiosRequestConfig) {
        this.method = "DELETE";
        this.url = this.baseURL + path;

        return { ...config, ...this };
    }

    public post(path: string, data?: any, config?: AxiosRequestConfig) {
        this.method = "POST";
        this.url = this.baseURL + path;
        this.data = data;

        return { ...config, ...this };
    }

    public put(path: string, data?: any, config?: AxiosRequestConfig) {
        this.method = "PUT";
        this.url = this.baseURL + path;
        this.data = data;

        return { ...config, ...this };
    }

    public patch(path: string, data?: any, config?: AxiosRequestConfig) {
        this.method = "PATCH";
        this.url = this.baseURL + path;
        this.data = data;

        return { ...config, ...this };
    }
}

export const base = () => {
    const requestConfig = new RequestConfig(env.PUBLIC_API_URL);

    requestConfig.headers = {...requestConfig.headers, "Content-Type": "application/json"}
    // if (getCookie("accessToken")) {
    //     requestConfig.setAuthorization(getCookie("accessToken"));
    // }
    // if (getCookie("uuid")) {
    //     requestConfig.setUuid(getCookie("uuid"));
    // }

    return axios.create(requestConfig);
};