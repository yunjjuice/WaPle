import axios from 'axios';

export default axios.create({
  baseURL: 'http://i3a204.p.ssafy.io:8888/',
  headers: {
    'Content-Type': 'application/json',
  },
});
