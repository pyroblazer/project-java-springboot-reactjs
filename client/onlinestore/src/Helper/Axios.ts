import axios from "axios";

interface FetchOptions {
  url: string;
  method: "get" | "post" | "put" | "delete" | string; 
  data?: any; 
}

const axiosFetch = async (options: FetchOptions): Promise<any> => {
  try {
    const token = sessionStorage.getItem("token") || ""; 

    const response = await axios.request({
      url: `http://localhost:9090/${options.url}`,
      method: options.method,
      data: options.data,
      headers: {
        Authorization: token ? `Bearer ${token}` : "",
      },
    });

    return response;
  } catch (err) {
    return Promise.reject(err);
  }
};

export default axiosFetch;
