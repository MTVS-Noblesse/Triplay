import axiosInstance from './axios.instance';

export const PostAxiosInstance = async (url, data, config) => {
    const res = await axiosInstance.post(url, data, config);
    return res;
};

export const GetAxiosInstance = async (url, config) => {
    const res = await axiosInstance.get(url, config);
    return res;
};

export const PatchAxiosInstance = async (url, data, config) => {
    const res = await axiosInstance.patch(url, data, config);
    return res;
};

// @ts-ignore
export const DeleteAxiosInstance = async (url, config) => {
    const res = await axiosInstance.delete(url, config);
    return res;
};
