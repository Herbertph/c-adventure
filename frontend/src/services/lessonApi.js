import { createApi } from './api'

const lessonApi = createApi(import.meta.env.VITE_API_LESSON_URL)

export default lessonApi
