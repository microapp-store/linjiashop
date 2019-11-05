const getters = {
  category: state => state.category,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  user: state => state.user.name
}
export default getters
