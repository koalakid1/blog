// Api.js
import axios, {type Method} from "axios";
import {env} from "$env/dynamic/public";

// Create a instance of axios to use the same base url.
const axiosAPI = axios.create({
    baseURL: env.PUBLIC_API_URL, // it's not recommended to have this info here.
});

// implement a method to execute all the request from here.
const apiRequest = (method: Method, url: string, request?: any) => {
    const headers = {
        authorization: "",
    };
    //using the axios instance to perform the request that received from each http method
    return axiosAPI({
        method,
        url,
        data: request,
        headers,
    })
        .then((res) => {
            return Promise.resolve(res.data);
        })
        .catch((err) => {
            return Promise.reject(err);
        });
};

// function to execute the http get request
const get = (url: string, request?: any) => apiRequest("get", url, request);

// function to execute the http delete request
const deleteRequest = (url: string, request?: any) => apiRequest("delete", url, request);

// function to execute the http post request
const post = (url: string, request?: any) => apiRequest("post", url, request);

// function to execute the http put request
const put = (url: string, request?: any) => apiRequest("put", url, request);

// function to execute the http path request
const patch = (url: string, request?: any) => apiRequest("patch", url, request);

// expose your method to other services or actions
const API = {
    get,
    delete: deleteRequest,
    post,
    put,
    patch,
};

export default API;