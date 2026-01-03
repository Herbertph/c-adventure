import { createApi } from './api'

const authApi = createApi(import.meta.env.VITE_API_AUTH_URL)

export default authApi
