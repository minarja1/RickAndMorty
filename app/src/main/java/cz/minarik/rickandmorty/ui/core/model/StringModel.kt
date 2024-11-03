package cz.minarik.rickandmorty.ui.core.model

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

/**
 * Model for storing information about string type and its value.
 */
sealed interface StringModel : java.io.Serializable {

    /**
     * String stored as string value.
     *
     * @property value value stored as String
     */
    data class String(val value: kotlin.String) : StringModel

    /**
     * String stored as html text.
     *
     * @property value value stored as html String
     */
    data class Html(val value: kotlin.String) : StringModel

    /**
     * String stored as resource with html format
     *
     * @property id of string resource
     */
    data class HtmlResource(@StringRes val id: Int) : StringModel {

        /**
         * Array of variable number of values that needs to be provided to dynamic strings.
         */
        var params: Array<Any>? = null
            private set

        /**
         * Constructor with variable arguments to support dynamic strings.
         */
        constructor(id: Int, vararg params: Any) : this(id) {
            this.params = arrayOf(*params)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as HtmlResource

            return id == other.id &&
                    params.contentEquals(other.params)
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + (params?.contentHashCode() ?: 0)
            return result
        }
    }

    /**
     * String stored as resource.
     *
     * @property id of string resource
     */
    data class Resource(@StringRes val id: Int) : StringModel {

        /**
         * Array of variable number of values that needs to be provided to dynamic strings.
         */
        var params: Array<Any>? = null
            private set

        /**
         * Constructor with variable arguments to support dynamic strings.
         */
        constructor(id: Int, vararg params: Any) : this(id) {
            this.params = arrayOf(*params)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Resource

            return id == other.id &&
                    params.contentEquals(other.params)
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + (params?.contentHashCode() ?: 0)
            return result
        }
    }

    /**
     * String composed from multiple resources.
     *
     * @property models string models to compose string. If it will be [ComposedString],
     * model will be ignored in [getString].
     * @property separator string that will placed between models.
     */
    class ComposedString(
        vararg val models: StringModel,
        val separator: kotlin.String = " ",
    ) : StringModel

    /**
     * String stored as plural resource.
     *
     * @property id of string resource
     * @property count The number used to get the correct string for the current language's plural rules.
     */
    class PluralResource(@PluralsRes val id: Int, val count: Int) : StringModel {

        /**
         * Array of variable number of values that needs to be provided to dynamic strings.
         */
        var params: Array<Any>? = null

        /**
         * Constructor with variable arguments to support dynamic plural strings.
         */
        constructor(id: Int, count: Int, vararg params: Any) : this(id, count) {
            this.params = arrayOf(*params)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as PluralResource
            return id == other.id &&
                    count == other.count &&
                    params.contentEquals(other.params)
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + count
            result = 31 * result + (params?.contentHashCode() ?: 0)
            return result
        }
    }

    /**
     * Empty string
     */
    data object Empty : StringModel

    /**
     * New line string
     */
    data object NewLine : StringModel

    /**
     * Function to simplify usage of [StringModel] in compose screens. It either returns string directly
     * (in case of [StringModel.String]) or gets string by calling [stringResource] function with given
     * resource ID and - if it is dynamic string - with varargs (in case of [StringModel.Resource]).
     *
     * @return String value of provided [StringModel]
     */
    @Composable
    fun getString(): kotlin.String = getString(LocalContext.current)

    /**
     * Function to simplify usage of [StringModel] in xml screens. It either returns string directly
     * (in case of [StringModel.String]) or gets string by calling [stringResource] function with given
     * resource ID and - if it is dynamic string - with varargs (in case of [StringModel.Resource]).
     *
     * @return String value of provided [StringModel]
     */
    fun getString(context: Context): kotlin.String {
        return when (this) {
            is Resource -> params?.map {
                if (it is StringModel) {
                    it.getString(context)
                } else {
                    it
                }
            }?.let {
                context.getString(id, *it.toTypedArray())
            } ?: context.getString(id)

            is ComposedString -> getStringFromComposedString(
                context,
                models
            ).joinToString(separator = separator)

            is String -> value
            is Html -> value
            is PluralResource -> params?.let {
                context.resources.getQuantityString(id, count, *it)
            } ?: context.resources.getQuantityString(id, count)

            Empty -> ""
            NewLine -> "\n"
            is HtmlResource -> params?.let {
                context.getString(id, *it)
            } ?: context.getString(id)
        }
    }

    /**
     * Get string from [ComposedString] with nested composed strings
     */
    private fun getStringFromComposedString(
        context: Context,
        models: Array<out StringModel>
    ): List<kotlin.String> {
        return models.flatMap { model ->
            if (model !is ComposedString) {
                listOf(model.getString(context))
            } else {
                getStringFromComposedString(context, model.models)
            }
        }
    }
}
