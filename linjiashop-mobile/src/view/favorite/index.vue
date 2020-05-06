<template>
    <div>

        <van-nav-bar
                title="喜欢的商品"
                left-text="返回"
                :right-text="rightText"
                left-arrow
                @click-left="onClickLeft"
                @click-right="onClickRight"
        />
        <div   v-for="item in favoriteList"
               :key="item.id"    v-show="!showEdit" >
            <van-card
                    :num="item.goods.stock"
                    :price="formatPrice(item.goods.price)"
                    :desc="item.goods.descript"
                    :title="item.goods.name"
                    :thumb="item.goods.img"
                    @click="viewGoodsDetail(item.goods.id)"
            />
        </div>

        <van-checkbox-group class="card-goods" v-model="checkedFavorites"  v-show="showEdit">
            <div   v-for="item in favoriteList"
                   :key="item.id"    class="favorite-item">
            <van-checkbox
                    :name="item.id"

            ></van-checkbox>
            <van-card
                    class="favorite-card"
                      :price="formatPrice(item.goods.price)"
                      :desc="item.goods.descript"
                      :title="item.goods.name"
                      :thumb="item.goods.img"
                      @click="viewGoodsDetail(item.goods.id)"
            />
            </div>

        </van-checkbox-group>
        <br><br>
        <van-submit-bar
                v-show="showEdit"
                :disabled="!checkedFavorites.length"
                button-text="删除"
                @submit="submit"
        >
            <van-checkbox v-model="checkedAll" @click="checkAll" class="favorite-checkall">全选</van-checkbox>
        </van-submit-bar>
    </div>
</template>

<script src="./favorite.js"></script>

<style lang="less">
    .favorite {
        &-checkall{
            padding: 0px 10px;
            margin-right:45%;
        }
        &-card{
            margin-left:25px;
        }
        &-item {
            position: relative;
            background-color: #fafafa;

            .van-checkbox__label {
                width: 100%;
                height: auto; // temp
                padding: 0 10px 0 15px;
                box-sizing: border-box;
            }

            .van-checkbox__icon {
                top: 50%;
                left: 10px;
                z-index: 1;
                position: absolute;
                margin-top: -10px;
            }

            .van-card__price {
                color: #f44;
            }
        }
    }
</style>
