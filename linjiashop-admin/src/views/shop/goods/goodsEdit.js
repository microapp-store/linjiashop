import editorImage from '@/components/Tinymce'
import plugins from '@/components/editContainer/plugins'
import toolbar from '@/components/editContainer/toolbar'
import {get, getList, save} from '@/api/shop/goods'
import {getCategories} from '@/api/shop/category'
import {getApiUrl} from '@/utils/utils'
import {getToken} from '@/utils/auth'

export default {
  components: {editorImage},
  props: {
    id: {
      type: String,
      default: function () {
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
      spec:'one',
      specs:[],
      specDialogFormVisible:false,
      specsForm:{},
      attrKeySel:'',
      attrKeyList:[],
      showAddAttrKey:false,
      attrKeyForm:{attrName:''},
      attrValForm:{attrVal:''},
      attrValSel:'',
      attrValListSel:[
        {id:1,attrVal:'白'},
        {id:2,attrVal:'黑'}
      ],
      form: {
        pic:'',
        gallery:[],
        idCategory:''
      },
      skuList:[],
      uploadUrl: '',
      uploadFileId: '',
      uploadHeaders: {
        'Authorization': ''
      },
      idGoods: '',
      active: 0,
      categories: [],
      tinymceId: 'tinymceId',
      fullscreen: false,
      languageTypeList: {
        'en': 'en',
        'zh': 'zh_CN'
      },
      galleryList: [],
      apiUrl: getApiUrl()
    }
  },
  computed: {
    language() {
      return this.languageTypeList[this.$store.getters.language]
    },
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
      this.uploadUrl = getApiUrl() + '/file'
      this.uploadHeaders['Authorization'] = getToken()
      this.idGoods = this.$route.query.id
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      if(this.idGoods) {
        get(this.idGoods).then(response => {
          this.form = response.data.goods
          this.skuList = response.data.skuList
          let galleryArr =this.form .gallery.split(',')
          for (let i = 0; i < galleryArr.length; i++) {
            if (galleryArr[i] != '') {
              this.galleryList.push({
                id: galleryArr[i],
                url: this.apiUrl + '/file/getImgStream?idFile=' + galleryArr[i]
              })
            }
          }
          this.setContent(this.form .detail)
        })
      }
      getCategories().then(response => {
        this.categories = response.data
      })
    },
    prev() {
      if (this.active > 0) {
        this.active -= 1
      }
    },
    save() {
      if (this.active < 3) {
        this.active++
        return
      }
      const content = this.getContent()
      const gallery = this.getGallery()

      save({
        name: this.form.name,
        pic: this.form.pic,
        gallery: gallery,
        idCategory: this.form.idCategory,
        descript: this.form.descript,
        detail: content,
        specifications: this.form.specifications,
        num: this.form.num,
        price: this.form.price,
        isDelete: this.form.isDelete,
        isOnSale: this.form.isOnSale,
        id: this.form.id
      }).then(response => {
        this.$message({
          message: this.$t('common.optionSuccess'),
          type: 'success'
        })
        this.$router.push('/goods')
      })

    },
    getGallery() {
      let gallery = ''
      for (let i = 0; i < this.galleryList.length; i++) {
        if (i == 0) {
          gallery = this.galleryList[i].id
        } else {
          gallery += (',' + this.galleryList[i].id)
        }

      }
      return gallery
    },
    handleRemove: function (file, fileList) {
      for (var i = 0; i < this.galleryList.length; i++) {
        if (this.galleryList[i].id === file.id) {
          this.galleryList.splice(i, 1)
        }
      }

    },
    handleUploadPicSuccess(response, raw) {
      console.log('response',response)
      if (response.code === 20000) {
        this.form.pic = response.data.id
        console.log('form.pic',this.form.pic)
      } else {
        this.$message({
          message: this.$t('common.uploadError'),
          type: 'error'
        })
      }
    },
    handleUploadGallerySuccess(response, raw) {
      console.log('response',response)
      console.log('gallerylist',this.galleryList)
      if (response.code === 20000) {
        this.galleryList.push(
          {
            id: response.data.id,
            url: this.apiUrl + '/file/getImgStream?idFile=' + response.data.id
          }
        )
      } else {
        this.$message({
          message: this.$t('common.uploadError'),
          type: 'error'
        })
      }
    },

    initTinymce() {
      const _this = this
      window.tinymce.init({
        value: '',
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
      return window.tinymce.get(this.tinymceId).getContent()
    },
    imageSuccessCBK(arr) {
      const _this = this
      arr.forEach(v => {
        window.tinymce.get(_this.tinymceId).insertContent(`<img class="wscnph" src="${v.url}" >`)
      })
    },
    tableRowClassName(row, index) {
      if (index === 1) {
        return 'info-row'
      } else if (index === 3) {
        return 'positive-row'
      }
      return ''
    },
    handleDelete(index) {
      this.specs.splice(index, 1)
    },
    addAttrKeyFun() {
      this.showAddAttrKey = !this.showAddAttrKey
    },
    submitAttrKeyForm() {
      console.log('submitAttrKeyForm')
      if( this.attrKeyForm.attrName != ''){
        let duplicationKey = false;
        for(let i=0;i<this.attrKeyList.length;i++){
          if(this.attrKeyForm.attrName === this.attrKeyList[i].attrName){
            duplicationKey = true;
            break;
          }
        }
        if(!duplicationKey){
          const id = this.attrKeyList.length+1;
          this.attrKeyList.push({
            id:id,
            attrName:this.attrKeyForm.attrName
          })
          this.attrKeySel = id;
          this.addAttrKeyFun();
        }
      }
      //提交属性名
      //提交成功后，将属性名自动选择当前属性名
      //提交成功后自动收起添加属性名表单，并且将并单清空重置
    },
    addSpec() {
      console.log('addSpec')
    }

  }
}
