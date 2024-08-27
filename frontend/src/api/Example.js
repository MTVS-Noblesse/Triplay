import { GetAxiosInstance, PostAxiosInstance } from '../../axios/axios.method';

export const getSubmitInfo = async (postId) => {
    const response = await GetAxiosInstance(`/room/post/${postId}/submit`);
    return response;
};

export const submitImage = async (formData) => {
    const response = await PostAxiosInstance('/user/s3', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
    });
    return response;
};

export const submitAll = async (content, imageURLs, postId) => {
    const response = await PostAxiosInstance(`/room/post/${postId}/submit`, {
        content: content,
        imageURLs: imageURLs,
    });
    return response;
};

export const getNoticeRoomInfo = async (roomId) => {
    const response = await GetAxiosInstance(`/room/${roomId}`);
    return response;
};
