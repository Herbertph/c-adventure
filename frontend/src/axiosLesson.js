import axios from 'axios'

const lessonApi = axios.create({
  baseURL: 'http://localhost:8081',
})

export default lessonApi