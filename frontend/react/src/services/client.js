import axios from 'axios';

export const getCustomers = async () => {
    try{
        const viteapibaseurl = import.meta.env.VITE_API_BASE_URL;
        return await axios
            .get(`${viteapibaseurl}/api/v1/customers`)
    }catch (exception) {
        throw exception;
    }
}