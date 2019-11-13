import editorImage from '@/components/Tinymce'
import plugins from '@/components/editContainer/plugins'
import toolbar from '@/components/editContainer/toolbar'
import { get, getList, save } from '@/api/shop/goods'
import { getCategories } from '@/api/shop/category'
import { getApiUrl } from '@/utils/utils'
export default {
  components: { editorImage },
  props:{
    id: {
      type: String,
      default: function() {
        return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
      }
    },
    value: {
      type: String,
      default: ''
    },
    toolbar: {
      type: Array,
      required: false,
      default() {
        return []
      }
    },
    menubar: {
      type: String,
      default: 'file edit insert view format table'
    },
    height: {
      type: Number,
      required: false,
      default: 360
    }
  },
  data() {
    return {
      form: {
        name:'',
        pic:'',
        gallery:'',
        idCategory:'',
        detail:'',
        specifications:'',
        id: ''
      },
      form2:{},
      form3:{},
      form4:{},
      id:'',
      active:0,
      categories:[],
      tinymceId: this.id,
      fullscreen: false,
      languageTypeList: {
        'en': 'en',
        'zh': 'zh_CN'
      },
      apiUrl:getApiUrl()
    }
  },
  computed: {
    language() {
      return this.languageTypeList[this.$store.getters.language]
    },
    //表单验证
    rules() {
      return {
        // cfgName: [
        //   { required: true, message: this.$t('config.name') + this.$t('common.isRequired'), trigger: 'blur' },
        //   { min: 3, max: 2000, message: this.$t('config.name') + this.$t('config.lengthValidation'), trigger: 'blur' }
        // ]
      }
    }
  },

  watch: {
    value(val) {
      if (!this.hasChange && this.hasInit) {
        this.$nextTick(() =>
          window.tinymce.get(this.tinymceId).setContent(val || ''))
      }
    },
    language() {
      this.destroyTinymce()
      this.$nextTick(() => this.initTinymce())
    }
  },
  mounted() {
    this.init()
    this.initTinymce()
  },
  activated() {
    this.initTinymce()
  },
  deactivated() {
    this.destroyTinymce()
  },
  destroyed() {
    this.destroyTinymce()
  },
  methods: {
    init() {
      this.id = this.$route.query.id
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      get(this.id).then(response => {
        this.form = response.data
        this.setContent(response.data.detail)
      })
      getCategories().then( response => {
        this.categories = response.data
      })
    },
    prev(){
      if(this.active>0){
        this.active -= 1
      }
    },
    save() {
      if (this.active++ > 2) this.active = 0
      return
      this.$refs['form'].validate((valid) => {
        if (valid) {
          save({
      name:this.form.name,
      pic:this.form.pic,
      gallery:this.form.gallery,
      idCategory:this.form.idCategory,
      detail:this.form.detail,
      specifications:this.form.specifications,
            id: this.form.id
          }).then(response => {
            this.$message({
              message: this.$t('common.optionSuccess'),
              type: 'success'
            })
            this.fetchData()
            this.formVisible = false
          })
        } else {
          return false
        }
      })
    },

    initTinymce() {
      const _this = this
      window.tinymce.init({
        value: 'aaaaaa',
        language: this.language,
        selector: `#${this.tinymceId}`,
        height: this.height,
        body_class: 'panel-body ',
        object_resizing: false,
        toolbar: this.toolbar.length > 0 ? this.toolbar : toolbar,
        menubar: this.menubar,
        plugins: plugins,
        end_container_on_empty_block: true,
        powerpaste_word_import: 'clean',
        code_dialog_height: 450,
        code_dialog_width: 1000,
        advlist_bullet_styles: 'square',
        advlist_number_styles: 'default',
        imagetools_cors_hosts: ['www.tinymce.com', 'codepen.io'],
        default_link_target: '_blank',
        link_title: false,
        nonbreaking_force_tab: true, // inserting nonbreaking space &nbsp; need Nonbreaking Space Plugin
        init_instance_callback: editor => {
          if (_this.value) {
            editor.setContent(_this.value)
          }
          _this.hasInit = true
          editor.on('NodeChange Change KeyUp SetContent', () => {
            this.hasChange = true
            const content = editor.getContent()
            this.form.content = content
          })
        },
        setup(editor) {
          editor.on('FullscreenStateChanged', (e) => {
            _this.fullscreen = e.state
          })
        }
      })
    },
    destroyTinymce() {
      const tinymce = window.tinymce.get(this.tinymceId)
      if (this.fullscreen) {
        tinymce.execCommand('mceFullScreen')
      }

      if (tinymce) {
        tinymce.destroy()
      }
    },
    setContent(value) {
      window.tinymce.get(this.tinymceId).setContent(value)
    },
    getContent() {
      window.tinymce.get(this.tinymceId).getContent()
    },
    imageSuccessCBK(arr) {
      const _this = this
      arr.forEach(v => {
        window.tinymce.get(_this.tinymceId).insertContent(`<img class="wscnph" src="${v.url}" >`)
      })
    },

  }
}
